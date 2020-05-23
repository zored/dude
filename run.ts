#!/usr/bin/env -S deno run --allow-write --allow-read
import { green } from "https://deno.land/std@0.52.0/fmt/colors.ts";
import { parse, Args } from "https://deno.land/std/flags/mod.ts";
import { Info } from "https://raw.githubusercontent.com/zored/deno/v0.0.13/mod.ts";
const { args, writeTextFileSync, exit, chmodSync } = Deno;

const success = (s: string) => console.log(green(`${s} 😊`));
const info = () => {
  new Info().updateFiles(["README.md"]);
  success("Info files updated!");
};
const commands = {
  info,
  hooks: (args: Args) =>
    new GitHooks()
      .setHandler("pre-commit", info)
      .run(args),
};

type Arg = (string | number);
class Commands {
  constructor(private commands: Record<string, (tailArgs: Args) => void>) {}
  run(): number {
    const parsedArgs = parse(args);
    const [name, ...tail] = parsedArgs._;
    parsedArgs._ = tail;

    const command = this.commands[name];
    if (command) {
      try {
        command(parsedArgs);
      } catch (e) {
        console.error(e);
        return 1;
      }
      return 0;
    }
    const names = Object.keys(this.commands).join(", ");
    console.error(`Unknown command: ${name}.\nExpected commands: ${names}`);
    return 1;
  }
}

type GitHookName =
  | "applypatch-msg"
  | "pre-applypatch"
  | "post-applypatch"
  | "pre-commit"
  | "prepare-commit-msg"
  | "commit-msg"
  | "post-commit"
  | "pre-rebase"
  | "post-checkout"
  | "post-merge"
  | "pre-receive"
  | "update"
  | "post-receive"
  | "post-update"
  | "pre-auto-gc"
  | "post-rewrite"
  | "pre-push";
type GitHookHandler = (args: Args) => void;
class GitHooks {
  private readonly handlers: Partial<Record<GitHookName, GitHookHandler>> = {};

  constructor(private scriptPath = "./run.ts hooks") {
  }

  setHandler(hook: GitHookName, handler: GitHookHandler): GitHooks {
    this.handlers[hook] = handler;
    return this;
  }

  run(args: Args): void {
    const { _ } = args;
    if (_.length === 0) {
      this.updateHookFiles();
      return;
    }

    const name = _[0] as GitHookName;
    args._ = _.slice(1);

    const handle = this.handlers[name];
    if (!handle) {
      throw new Error(`Git hook "${name}" handle is undefined.`);
    }
    handle(args);
  }

  private updateHookFiles(): void {
    (Object.keys(this.handlers) as GitHookName[]).forEach((name) =>
      this.updateHookFile(name)
    );
    success("Git hook files updated");
  }

  private updateHookFile(name: GitHookName): void {
    const path = `.git/hooks/${name}`;
    writeTextFileSync(
      path,
      ["#!/bin/sh", `${this.scriptPath} ${name} "$@"`, ""].join("\n"),
    );
    chmodSync(path, 0o755);
  }
}

exit(new Commands(commands).run());

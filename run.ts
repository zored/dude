#!/usr/bin/env -S deno run --allow-write --allow-read
import { Args } from "https://deno.land/std/flags/mod.ts";
import { Info, Commands, GitHooks } from "https://raw.githubusercontent.com/zored/deno/v0.0.16/mod.ts";

const info = () => new Info().updateFiles(["README.md"]);
const hooks = new GitHooks({"pre-commit": info});
new Commands({
  info,
  hooks: (args: Args) => hooks.run(args),
}).runAndExit();

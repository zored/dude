#!/usr/bin/env -S deno run --allow-write --allow-read --allow-run
import { Args } from "https://deno.land/std/flags/mod.ts";
import {
  Info,
  Commands,
  GitHooks,
  Runner,
} from "https://raw.githubusercontent.com/zored/deno/v0.0.18/mod.ts";
import { DepChecker } from "https://raw.githubusercontent.com/zored/deno/v0.0.18/src/data/dep-check.ts";
const { cwd } = Deno;

const info = () => new Info().updateFiles(["README.md"]);
const fmt = () => new Runner().run(`deno fmt ./run.ts`);
const arch = () => new DepChecker().byPaths(cwd(), cwd() + '/dep-check.json');

const gitHooks = new GitHooks({
  "pre-commit": () => {
    info();
    arch();
  },
});
const hooks = (args: Args) => gitHooks.run(args);

new Commands({ info, fmt, hooks, arch }).runAndExit();

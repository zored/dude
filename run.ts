#!/usr/bin/env -S deno run --allow-write --allow-read --allow-run
import { Args } from "https://deno.land/std/flags/mod.ts";
import {
  Info,
  Commands,
  GitHooks,
  Runner,
} from "https://raw.githubusercontent.com/zored/deno/v0.0.18/mod.ts";
import { DepFactory } from "https://raw.githubusercontent.com/zored/deno/v0.0.18/src/data/dep-check.ts";

const info = () => new Info().updateFiles(["README.md"]);
const fmt = () => new Runner().run(`deno fmt ./run.ts`);
const arch = () => new DepFactory().allByPath("src/main/java");

const gitHooks = new GitHooks({
  "pre-commit": () => {
    info();
    arch();
  },
});
const hooks = (args: Args) => gitHooks.run(args);

new Commands({ info, fmt, hooks, arch }).runAndExit();

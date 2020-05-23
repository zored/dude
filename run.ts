#!/usr/bin/env -S deno run --allow-write --allow-read
import { green } from "https://deno.land/std@0.52.0/fmt/colors.ts";
import { Args } from "https://deno.land/std/flags/mod.ts";
import {
  Info,
  Commands,
  GitHooks,
} from "https://raw.githubusercontent.com/zored/deno/v0.0.16/mod.ts";

const success = (s: string) => console.log(green(s));
const info = () => {
  new Info().updateFiles(["README.md"]);
  success("Updated info files");
};
const hooks = new GitHooks({ "pre-commit": info });
new Commands({
  info,
  hooks: (args: Args) => {
    hooks.run(args);
    success("Ran git hooks")
  },
}).runAndExit();

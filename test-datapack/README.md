# Command Limit Tracer test datapack

For Minecraft Java Edition 1.20.4. This datapack contains a function that
recursively calls itself to trigger the command execution limit. It does
nothing unless you explicitly run it.

1. Copy the `test-datapack` folder into the world's `datapacks` directory.
2. Record the current limit, then lower it to keep the test short:
   ```
   /gamerule maxCommandChainLength
   /gamerule maxCommandChainLength 100
   ```
3. Reload datapacks and run the test from the server console:
   ```
   /reload
   /function tracer_test:loop
   ```
4. Check the server log for the command-limit message and trace entries
   containing `tracer_test:loop`.
5. Restore the original `maxCommandChainLength` value.
6. Remove `test-datapack` from the world's `datapacks` directory when finished.

The function is intentionally recursive; do not run it before lowering the
command-chain limit.

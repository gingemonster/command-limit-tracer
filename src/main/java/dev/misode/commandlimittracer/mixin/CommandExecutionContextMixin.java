package dev.misode.commandlimittracer.mixin;

import dev.misode.commandlimittracer.Trace;
import dev.misode.commandlimittracer.Traceable;
import net.minecraft.command.CommandExecutionContext;
import net.minecraft.command.CommandQueueEntry;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;

@Mixin(CommandExecutionContext.class)
public class CommandExecutionContextMixin {
    @Unique
    private final ArrayList<Trace> traces = new ArrayList<>();

    @Redirect(
        method = "run",
        at = @At(
            value = "INVOKE",
            target = "Lorg/slf4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V",
            remap = false
        )
    )
    private void reportCommandLimit(Logger logger, String message, Object commandLimit) {
        logger.info(message, commandLimit);
        if (traces.size() > 10) {
            for (int i = traces.size() - 1; i >= traces.size() - 5; i--) {
                logger.info("  {}", traces.get(i));
            }
            logger.info("  ({} hidden calls)", traces.size() - 10);
            for (int i = 4; i >= 0; i--) {
                logger.info("  {}", traces.get(i));
            }
        } else {
            for (int i = traces.size() - 1; i >= 0; i--) {
                logger.info("  {}", traces.get(i));
            }
        }
    }

    @Redirect(
        method = "run",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/command/CommandQueueEntry;execute(Lnet/minecraft/command/CommandExecutionContext;)V"
        )
    )
    private <T> void executeQueueEntry(CommandQueueEntry<T> entry, CommandExecutionContext<T> executionContext) {
        String info = ((Traceable)(Object)entry).commandlimittracer$getTraceInfo();
        if (info != null) {
            this.pushTrace(entry.frame().depth(), info);
        }
        entry.execute(executionContext);
    }

    @Unique
    private void pushTrace(int depth, String info) {
        traces.removeIf(trace -> trace.depth() >= depth);
        traces.add(new Trace(depth, info));
    }
}

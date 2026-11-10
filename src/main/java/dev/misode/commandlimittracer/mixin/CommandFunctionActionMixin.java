package dev.misode.commandlimittracer.mixin;

import dev.misode.commandlimittracer.Traceable;
import net.minecraft.command.CommandFunctionAction;
import net.minecraft.command.CommandQueueEntry;
import net.minecraft.command.SteppedCommandAction;
import net.minecraft.server.function.Procedure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CommandFunctionAction.class)
public class CommandFunctionActionMixin {
    @Shadow
    @Final
    private Procedure<?> function;

    @ModifyArg(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/command/SteppedCommandAction;enqueueCommands(Lnet/minecraft/command/CommandExecutionContext;Lnet/minecraft/command/Frame;Ljava/util/List;Lnet/minecraft/command/SteppedCommandAction$ActionWrapper;)V"
        ),
        index = 3
    )
    private SteppedCommandAction.ActionWrapper<Object, Object> traceFunctionCall(
        SteppedCommandAction.ActionWrapper<Object, Object> wrapper
    ) {
        boolean[] traced = {false};
        return (frame, action) -> {
            CommandQueueEntry<Object> entry = wrapper.create(frame, action);
            if (!traced[0]) {
                ((Traceable)(Object)entry).commandlimittracer$setTraceInfo(this.function.id().toString());
                traced[0] = true;
            }
            return entry;
        };
    }
}

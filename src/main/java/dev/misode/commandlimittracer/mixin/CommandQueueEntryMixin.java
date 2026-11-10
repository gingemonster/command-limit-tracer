package dev.misode.commandlimittracer.mixin;

import dev.misode.commandlimittracer.Traceable;
import net.minecraft.command.CommandQueueEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CommandQueueEntry.class)
public class CommandQueueEntryMixin implements Traceable {
    @Unique
    private String traceInfo;

    @Override
    public void commandlimittracer$setTraceInfo(String trace) {
        this.traceInfo = trace;
    }

    @Override
    public String commandlimittracer$getTraceInfo() {
        return this.traceInfo;
    }
}

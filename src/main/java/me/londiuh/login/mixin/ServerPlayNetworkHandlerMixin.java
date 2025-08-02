package me.londiuh.login.mixin;

import me.londiuh.login.listeners.OnGameMessage;
import me.londiuh.login.listeners.OnPlayerAction;
import me.londiuh.login.listeners.OnPlayerMove;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {
    @Inject(method = "handleMovePlayer", at = @At("HEAD"), cancellable = true)
    public void onPlayerMove(ServerboundMovePlayerPacket par1, CallbackInfo ci) {
        ServerGamePacketListenerImpl self = ServerGamePacketListenerImpl.class.cast(this);
        if (!OnPlayerMove.canMove(self)) {
            ci.cancel();
        }
    }
    
    @Inject(method = "handlePlayerAction", at = @At("HEAD"), cancellable = true)
    public void onPlayerAction(ServerboundPlayerActionPacket pPacket, CallbackInfo ci) {
        ServerGamePacketListenerImpl self = ServerGamePacketListenerImpl.class.cast(this);
        if (!OnPlayerAction.canInteract(self)) {
            ci.cancel(); // TODO: breaking a block desyncs with server
        }
    }

    @Inject(method = "handleInteract", at = @At("HEAD"), cancellable = true)
    public void onPlayerAction(ServerboundInteractPacket pPacket, CallbackInfo ci) {
        ServerGamePacketListenerImpl self = ServerGamePacketListenerImpl.class.cast(this);
        if (!OnPlayerAction.canInteract(self)) {
            ci.cancel(); // TODO: breaking a block desyncs with server
        }
    }

    @Inject(method = "handleUseItem", at = @At("HEAD"), cancellable = true)
    public void onPlayerAction(ServerboundUseItemPacket pPacket, CallbackInfo ci) {
        ServerGamePacketListenerImpl self = ServerGamePacketListenerImpl.class.cast(this);
        if (!OnPlayerAction.canInteract(self)) {
            ci.cancel(); // TODO: breaking a block desyncs with server
        }
    }

    @Inject(method = "handleUseItemOn", at = @At("HEAD"), cancellable = true)
    public void onPlayerAction(ServerboundUseItemOnPacket pPacket, CallbackInfo ci) {
        ServerGamePacketListenerImpl self = ServerGamePacketListenerImpl.class.cast(this);
        if (!OnPlayerAction.canInteract(self)) {
            ci.cancel(); // TODO: breaking a block desyncs with server
        }
    }

    @Inject(method = "handlePlayerInput", at = @At("HEAD"), cancellable = true)
    public void onPlayerAction(ServerboundPlayerInputPacket pPacket, CallbackInfo ci) {
        ServerGamePacketListenerImpl self = ServerGamePacketListenerImpl.class.cast(this);
        if (!OnPlayerAction.canInteract(self)) {
            ci.cancel(); // TODO: breaking a block desyncs with server
        }
    }

    @Inject(method = "handleChat(Lnet/minecraft/network/protocol/game/ServerboundChatPacket;)V", at = @At("HEAD"), cancellable = true)
    public void onGameMessage(ServerboundChatPacket pPacket, CallbackInfo ci) {
        ServerGamePacketListenerImpl self = ServerGamePacketListenerImpl.class.cast(this);
        if (!OnGameMessage.canSendMessage(self, pPacket)) {
            ci.cancel();
        }
    }
}

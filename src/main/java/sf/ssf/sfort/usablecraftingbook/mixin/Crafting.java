package sf.ssf.sfort.usablecraftingbook.mixin;

import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreen.class)
public abstract class Crafting extends HandledScreen<CraftingScreenHandler> {

	public Crafting(CraftingScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Shadow protected abstract void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType);

	@Inject(method="mouseClicked(DDI)Z", at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/screen/ingame/CraftingScreen;setFocused(Lnet/minecraft/client/gui/Element;)V"))
	private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir){
		if (button == 0)
			onMouseClick(handler.getSlot(handler.getCraftingResultSlotIndex()), handler.getCraftingResultSlotIndex(), 0, hasShiftDown() ? SlotActionType.QUICK_MOVE : SlotActionType.PICKUP);
	}
}
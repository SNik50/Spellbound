package com.ombremoon.spellbound.client.gui.guide_renderers;

import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.IPageElement;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.special.IClickable;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.special.IHoverable;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ElementRenderDispatcher {
    private  static final Map<Class<?>, Object> REGISTER = new HashMap<>();

    public static <T extends IPageElement, C extends IPageElementRenderer<T>> void register(Class<T> element, C renderer) {
        REGISTER.put(element, renderer);
    }

    public static <T extends IPageElement, C extends IPageElementRenderer<T>> void renderElement(T element, GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick) {
        C renderer = (C) REGISTER.get(element.getClass());
        renderer.render(element, graphics, leftPos, topPos, mouseX, mouseY, partialTick);
    }

    public static <T extends IPageElement, C extends IPageElementRenderer<T>> boolean isHovering(T element, int mouseX, int mouseY, int leftPos, int topPos) {
        C renderer = (C) REGISTER.get(element.getClass());
        return renderer.isHovering(mouseX, mouseY, leftPos, topPos, element);
    }

    public static <T extends IPageElement, C extends IPageElementRenderer<T>> void handleHover(T element, GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick) {
        C renderer = (C) REGISTER.get(element.getClass());
        renderer.handleHover(element, graphics, leftPos, topPos, mouseX, mouseY, partialTick);
    }

    public static <T extends IPageElement, C extends IPageElementRenderer<T>> void handleClick(T element) {
        C renderer = (C) REGISTER.get(element.getClass());
        renderer.handleClick(element);
    }

}

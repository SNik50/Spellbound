package com.ombremoon.spellbound.datagen.provider;

import com.ombremoon.spellbound.common.magic.acquisition.divine.ActionHolder;
import com.ombremoon.spellbound.common.magic.acquisition.divine.DivineAction;
import com.ombremoon.spellbound.common.magic.acquisition.guides.GuideBookPage;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.IPageElement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class GuideBookProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> registries;
    public static final int PAGE_TWO_START = 167;

    public GuideBookProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "guide_book");
        this.registries = registries;
    }

    public abstract void generate(HolderLookup.Provider registries, BiConsumer<ResourceLocation, GuideBookPage> writer);

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return this.registries.thenCompose(provider -> {
            Map<ResourceLocation, GuideBookPage> map = new HashMap<>();
            List<CompletableFuture<?>> list = new ArrayList<>();
            BiConsumer<ResourceLocation, GuideBookPage> consumer = (id, page) -> {
                if (map.get(id) != null) {
                    throw new IllegalStateException("Duplicate Guide Book Page " + id);
                } else {
                    Path path = this.pathProvider.json(id);
                    list.add(DataProvider.saveStable(output, provider, GuideBookPage.CODEC, page, path));
                }
            };
            this.generate(provider, consumer);

            return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public final String getName() {
        return "Guide Book";
    }
}

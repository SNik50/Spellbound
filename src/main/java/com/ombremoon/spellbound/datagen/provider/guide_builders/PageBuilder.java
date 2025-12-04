package com.ombremoon.spellbound.datagen.provider.guide_builders;

import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.acquisition.guides.GuideBookManager;
import com.ombremoon.spellbound.common.magic.acquisition.guides.GuideBookPage;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.*;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.*;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import com.ombremoon.spellbound.datagen.provider.GuideBookProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class PageBuilder {
    private ResourceLocation bookId;
    private ResourceLocation pageScrap;
    private ResourceLocation insertAfter;
    private List<IPageElement> elements;

    private PageBuilder(ResourceLocation bookId) {
        this.bookId = bookId;
        this.insertAfter = GuideBookManager.FIRST_PAGE;
        this.pageScrap = GuideBookManager.FIRST_PAGE;
        this.elements = new ArrayList<>();
    }

    /**
     * Creates a builder and specifies what book the page will be for.
     * @param bookId The id for the book the page belongs to
     * @return new Builder
     */
    public static PageBuilder forBook(ResourceLocation bookId) {
        return new PageBuilder(bookId);
    }

    /**
     * Sets the page scrap required to view this page. By default page is visible without any scrap.
     * @param pageScrap The identifier of the page scrap
     * @return this
     */
    public PageBuilder setRequiredScrap(ResourceLocation pageScrap) {
        this.pageScrap = pageScrap;
        return this;
    }

    /**
     * Sets the page that should be found before this one in the book
     * @param page the location of the previous page
     * @return this
     * @implNote This does not guarantee it goes straight after this page, just that it will be somewhere after it
     */
    public PageBuilder setPreviousPage(ResourceLocation page) {
        this.insertAfter = page;
        return this;
    }

    /**
     * Adds a page element to the page
     * @param element The element to add
     * @return this
     */
    public PageBuilder addElement(IPageElement element) {
        this.elements.add(element);
        return this;
    }

    /**
     * Adds multiple elements to the page
     * @param elements The elements to add
     * @return this
     */
    public PageBuilder addElements(IPageElement... elements) {
        this.elements.addAll(List.of(elements));
        return this;
    }

    /**
     * Constructs the GuideBookPage
     * @return GuideBookPage built using data provided
     */
    private GuideBookPage build() {
        return new GuideBookPage(bookId, pageScrap, insertAfter, elements);
    }

    /**
     * Writes the page to a file
     * @param writer the json writer
     * @param pageName Name/Location of the file
     */
    public void save(BiConsumer<ResourceLocation, GuideBookPage> writer, ResourceLocation pageName) {
        writer.accept(pageName, build());
    }

    //Builder for a GuideRecipe
    public static class Recipe {
        private final ResourceLocation recipe;
        private String gridName;
        private float scale;
        private ElementPosition position;
        private ResourceLocation pageScrap;

        private Recipe(ResourceLocation recipe) {
            this.recipe = recipe;
            this.gridName = "basic";
            this.scale = 1f;
            this.position = ElementPosition.getDefault();
            this.pageScrap = GuideBookManager.FIRST_PAGE;
        }

        /**
         * Creates a new GuideRecipe builder for the given recipe
         * @param recipe The recipe to display
         * @return new GuideRecipe builder
         * @see GuideRecipe
         */
        public static Recipe of(ResourceLocation recipe) {
            return new Recipe(recipe);
        }

        /**
         * The grid texture to use, defaults to "basic"
         * @param gridName The name of the grid to use, not including textures/... or .png
         * @return this
         * @see Recipe.SpellboundGrids
         * @see Recipe#gridName(SpellboundGrids)
         */
        public Recipe gridName(String gridName) {
            this.gridName = gridName;
            return this;
        }

        /**
         * Sets the grid texture for the recipe to use, using an enum of Spellbound made grids. Defaults to SpellboundGrids.BASIC
         * @param grid The grid texture to use
         * @return this
         */
        public Recipe gridName(SpellboundGrids grid) {
            return gridName(grid.name().toLowerCase());
        }

        /**
         * Sets the scale of the recipe. Defaults to 1f
         * @param scale Multiplier scale of recipe element
         * @return this
         */
        public Recipe scale(float scale) {
            this.scale = scale;
            return this;
        }

        /**
         * Sets the position the element appears on the page
         * @param x the x offset from left of the book
         * @param y the y offset from top of the book
         * @return this
         */
        public Recipe position(int x, int y) {
            this.position = new ElementPosition(x, y);
            return this;
        }

        /**
         * Sets the page scrap required to unlock this element. Will be obfuscated if not unlocked
         * @param scrap The id for the page scrap
         * @return this
         */
        public Recipe setRequiredScrap(ResourceLocation scrap) {
            this.pageScrap = scrap;
            return this;
        }

        /**
         * Turns the given data into a GuideRecipe element
         * @return constructed GuideRecipe
         */
        public GuideRecipe build() {
            return new GuideRecipe(recipe, gridName, scale, position, new RecipeExtras(pageScrap));
        }

        /**
         * List of Crafting grid textures used in Spellbound
         */
        public enum SpellboundGrids {
            BASIC,
            ARCHITECT,
            CODEX,
            GRIMOIRE,
            NECRONOMICON,
            SWINDLER
        }
    }

    //Builder for a GuideEntityRenderer
    public static class EntityRenderer {
        private ResourceLocation entity;
        private ElementPosition position;
        private boolean followMouse;
        private int scale;
        private ResourceLocation pageScrap;

        private EntityRenderer(EntityType<?> entity) {
            this.entity = BuiltInRegistries.ENTITY_TYPE.getKey(entity);
            this.position = ElementPosition.getDefault();
            this.followMouse = false;
            this.scale = 25;
            this.pageScrap = GuideBookManager.FIRST_PAGE;
        }

        /**
         * Creates a new EntityRenderer builder using a given entity
         * @param entity the entity to render
         * @return new GuideEntityRenderer builder
         * @see GuideEntityRenderer
         */
        public static EntityRenderer of(EntityType<?> entity) {
            return new EntityRenderer(entity);
        }

        /**
         * Makes the entity renderer follow the mouse like the player in the inventory, defaults to false
         * @return this
         */
        public EntityRenderer followsMouse() {
            this.followMouse = true;
            return this;
        }

        /**
         * How the entity should be scaled, defaults to 25
         * @param scale multiplicative scalar
         * @return this
         */
        public EntityRenderer scale(int scale) {
            this.scale = scale;
            return this;
        }

        /**
         * Sets the position the element appears on the page
         * @param x the x offset from left of the book
         * @param y the y offset from top of the book
         * @return this
         */
        public EntityRenderer position(int x, int y) {
            this.position = new ElementPosition(x, y);
            return this;
        }

        /**
         * Sets the page scrap required to unlock this element. Will be obfuscated if not unlocked
         * @param scrap The id for the page scrap
         * @return this
         */
        public EntityRenderer setRequiredScrap(ResourceLocation scrap) {
            this.pageScrap = scrap;
            return this;
        }

        public GuideEntityRenderer build() {
            return new GuideEntityRenderer(
                    entity,
                    new EntityRendererExtras(
                            pageScrap,
                            followMouse,
                            scale
                    ), position
            );
        }
    }

    //Builder for a GuideItemList
    public static class ItemList {
        private List<GuideItemList.ItemListEntry> entries;
        private ElementPosition position;
        private ResourceLocation pageScrap;
        private int maxRows;
        private int rowGap;
        private int columnGap;
        private int countGap;
        private boolean dropShadow;
        private int textColour;

        private ItemList() {
            this.entries = new ArrayList<>();
            this.position = ElementPosition.getDefault();
            this.pageScrap = GuideBookManager.FIRST_PAGE;
            this.maxRows = 0;
            this.rowGap = 20;
            this.columnGap = 45;
            this.countGap = 33;
            this.dropShadow = false;
            this.textColour = 0;
        }

        /**
         * Creates a builder for GuideItemList
         * @return new ItemList builder
         */
        public static ItemList of() {
            return new ItemList();
        }

        /**
         * Adds a new entry to the item list
         * @param item The item to add to the list
         * @param count the number of the item to add
         * @return this
         */
        public ItemList addEntry(net.minecraft.world.item.Item item, int count) {
            this.entries.add(new GuideItemList.ItemListEntry(
                    BuiltInRegistries.ITEM.getKey(item), count));
            return this;
        }

        /**
         * Adds a single item to the item list
         * @param item The item to add
         * @return this
         */
        public ItemList addEntry(net.minecraft.world.item.Item item) {
            return addEntry(item, 1);
        }

        /**
         * Sets the position the element appears on the page
         * @param x the x offset from left of the book
         * @param y the y offset from top of the book
         * @return this
         */
        public ItemList position(int x, int y) {
            this.position = new ElementPosition(x, y);
            return this;
        }

        /**
         * Sets the page scrap required to unlock this element. Will be obfuscated if not unlocked
         * @param scrap The id for the page scrap
         * @return this
         */
        public ItemList setRequiredScrap(ResourceLocation scrap) {
            this.pageScrap = scrap;
            return this;
        }

        /**
         * The maximum rows that are displayed before going into a new column, defaults to 0 (no limit)
         * @param max number of rows
         * @return this
         */
        public ItemList maxRows(int max) {
            this.maxRows = max;
            return this;
        }

        /**
         * Sets the number of pixels between each row, defaults to 20
         * @param gap number of pixels
         * @return this
         */
        public ItemList rowGap(int gap) {
            this.rowGap = gap;
            return this;
        }

        /**
         * Sets the number of pixels between each column, defaults to 45
         * @param gap number of pixels
         * @return this
         */
        public ItemList columnGap(int gap) {
            this.columnGap = gap;
            return this;
        }

        /**
         * Sets the number of pixels between the count and the item, defaults to 33
         * @param gap number of pixels
         * @return this
         */
        public ItemList countGap(int gap) {
            this.countGap = gap;
            return this;
        }

        /**
         * Enables drop shadow on the text
         * @return this
         */
        public ItemList dropShadow() {
            this.dropShadow = true;
            return this;
        }

        /**
         * Sets the text colour, defaults to white
         * @param colour the colour to use
         * @return this
         */
        public ItemList textColour(int colour) {
            this.textColour = colour;
            return this;
        }

        public GuideItemList build() {
            return new GuideItemList(
                    entries,
                    new ItemListExtras(
                            pageScrap,
                            maxRows,
                            rowGap,
                            columnGap,
                            countGap,
                            dropShadow,
                            textColour
                    ), position
            );
        }
    }

    //Builder for GuideSpellInfo
    public static class SpellInfo {
        private ResourceLocation spell;
        private ElementPosition position;
        private int textColour;
        private int lineGap;
        private boolean dropShadow;
        private boolean mastery;
        private Display baseDamage;
        private Display castTime;
        private Display duration;
        private Display manaCost;
        private Display manaPerTick;
        private boolean alwaysShow;

        private SpellInfo(SpellType<?> spell) {
            this.spell = SBSpells.REGISTRY.getKey(spell);
            this.position = ElementPosition.getDefault();
            this.textColour = 0xFFFFFF;
            this.lineGap = 10;
            this.dropShadow = false;
            this.mastery = true;
            this.baseDamage = Display.IF_VALID;
            this.castTime = Display.IF_VALID;
            this.duration = Display.IF_VALID;
            this.manaCost = Display.IF_VALID;
            this.manaPerTick = Display.IF_VALID;
            this.alwaysShow = false;
        }

        /**
         * Creates a SpellInfo builder using a given spell
         * @param spellType the spell to show stats on
         * @return new SpellInfo builder
         */
        public static SpellInfo of(SpellType<?> spellType) {
            return new SpellInfo(spellType);
        }

        /**
         * Sets the position the element appears on the page
         * @param x the x offset from left of the book
         * @param y the y offset from top of the book
         * @return this
         */
        public SpellInfo position(int x, int y) {
            this.position = new ElementPosition(x, y);
            return this;
        }

        /**
         * The colour of the text, defaults to white
         * @param colour the colour value
         * @return
         */
        public SpellInfo textColour(int colour) {
            this.textColour = colour;
            return this;
        }

        /**
         * Sets the gap between each stat, 10
         * @param gap number of pixels
         * @return this
         */
        public SpellInfo lineGap(int gap) {
            this.lineGap = gap;
            return this;
        }

        /**
         * Enables drop shadow on the text
         * @return this
         */
        public SpellInfo dropShadow() {
            this.dropShadow = true;
            return this;
        }

        /**
         * Stops it from displaying the required mastery level
         * @return this
         */
        public SpellInfo hideMastery() {
            this.mastery = false;
            return this;
        }

        /**
         * Whether the base damage stat should be displayed
         * @param display Display setting
         * @return this
         */
        public SpellInfo baseDamage(Display display) {
            this.baseDamage = display;
            return this;
        }
        /**
         * Whether the cast time stat should be displayed
         * @param display Display setting
         * @return this
         */
        public SpellInfo castTime(Display display) {
            this.castTime = display;
            return this;
        }
        /**
         * Whether the duration stat should be displayed
         * @param display Display setting
         * @return this
         */
        public SpellInfo duration(Display display) {
            this.duration = display;
            return this;
        }
        /**
         * Whether the mana cost stat should be displayed
         * @param display Display setting
         * @return this
         */
        public SpellInfo manaCost(Display display) {
            this.manaCost = display;
            return this;
        }

        /**
         * Whether the mana per tick stat should be displayed
         * @param display Display setting
         * @return this
         */
        public SpellInfo manaPerTick(Display display) {
            this.manaPerTick = display;
            return this;
        }

        /**
         * Makes the stats show regardless of if the spell is unlocked or not
         * @return this
         */
        public SpellInfo alwaysShow() {
            this.alwaysShow = true;
            return this;
        }

        public GuideSpellInfo build() {
            return new GuideSpellInfo(
                    spell,
                    new SpellInfoExtras(
                            textColour,
                            lineGap,
                            dropShadow,
                            mastery,
                            baseDamage.getValue(),
                            castTime.getValue(),
                            duration.getValue(),
                            manaCost.getValue(),
                            manaPerTick.getValue(),
                            alwaysShow
                    ), position
            );
        }


        /**
         * How stats should be displayed
         * NEVER - won't appear on the element
         * IF_VALID - will only appear if value is > 0
         * ALWAYS - will appear on element always
         */
        public enum Display {
            NEVER(0),
            IF_VALID(1),
            ALWAYS(2);

            private final int value;

            Display(int i) {
                this.value = i;
            }

            public int getValue() {
                return value;
            }
        }
    }

    //TODO: Obfuscation
    //Builder for GuideImage
    public static class Image {
        private final ResourceLocation image;
        private int width;
        private int height;
        private ElementPosition position;
        private ResourceLocation pageScrap;

        private Image(ResourceLocation image) {
            this.image = image;
            this.position = ElementPosition.getDefault();
            this.pageScrap = GuideBookManager.FIRST_PAGE;
        }

        /**
         * Creates a new builder for GuideImage
         * @param image the texture location of the image
         * @return new GuideImage builder
         */
        public static Image of(ResourceLocation image) {
            return new Image(image);
        }

        /**
         * Sets the dimensions to use for display
         * @param width pixel width
         * @param height pixel height
         * @return this
         * @apiNote There is no default this must be set.
         */
        public Image setDimensions(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * Sets the position the element appears on the page
         * @param x the x offset from left of the book
         * @param y the y offset from top of the book
         * @return this
         */
        public Image position(int x, int y) {
            this.position = new ElementPosition(x, y);
            return this;
        }

        /**
         * Sets the page scrap required to unlock this element. Will be obfuscated if not unlocked
         * @param scrap The id for the page scrap
         * @return this
         */
        public Image setRequiredScrap(ResourceLocation scrap) {
            this.pageScrap = scrap;
            return this;
        }

        public GuideImage build() {
            return new GuideImage(image, width, height, position);
        }
    }

    //TODO: move scale to extras
    //Builder for GuideItem
    public static class Item {
        private final ResourceLocation item;
        private String tile;
        private float scale;
        private ElementPosition position;
        private ResourceLocation pageScrap;

        private Item(net.minecraft.world.item.Item item) {
            this.item = BuiltInRegistries.ITEM.getKey(item);
            this.tile = Recipe.SpellboundGrids.BASIC.name().toLowerCase();
            this.scale = 1f;
            this.position = ElementPosition.getDefault();
            this.pageScrap = GuideBookManager.FIRST_PAGE;
        }

        /**
         * Creates a new GuideItem builder for a given item
         * @param item The item to display
         * @return new GuideItem builder
         */
        public static Item of(net.minecraft.world.item.Item item) {
            return new Item(item);
        }

        /**
         * The grid texture to use, defaults to "basic"
         * @param tile The name of the grid to use, not including textures/... or .png
         * @return this
         * @see Recipe.SpellboundGrids
         * @see Item#tile(Recipe.SpellboundGrids)
         */
        public Item tile(String tile) {
            this.tile = tile;
            return this;
        }

        /**
         * Sets the tile using the built in spellbound grids
         * @param tile the tile to use
         * @return this
         */
        public Item tile(Recipe.SpellboundGrids tile) {
            return this.tile(tile.name().toLowerCase());
        }

        /**
         * The scale multiplier to apply to the element
         * @param scale multiplier value
         * @return this
         */
        public Item scale(float scale) {
            this.scale = scale;
            return this;
        }

        /**
         * Sets the position the element appears on the page
         * @param x the x offset from left of the book
         * @param y the y offset from top of the book
         * @return this
         */
        public Item position(int x, int y) {
            this.position = new ElementPosition(x, y);
            return this;
        }

        /**
         * Sets the page scrap required to unlock this element. Will be obfuscated if not unlocked
         * @param scrap The id for the page scrap
         * @return this
         */
        public Item setRequiredScrap(ResourceLocation scrap) {
            this.pageScrap = scrap;
            return this;
        }

        public GuideItem build() {
            return new GuideItem(
                    item,
                    tile,
                    scale,
                    position,
                    new ItemExtras(pageScrap)
            );
        }
    }

    //Builder for GuideText
    public static class Text {
        private String translation;
        private ElementPosition position;
        private ResourceLocation pageScrap;
        private int colour;
        private int maxLineLength;
        private int lineGap;
        private boolean dropShadow;
        private boolean textWrapping;

        private Text(String translation) {
            this.translation = translation;
            this.position = ElementPosition.getDefault();
            this.pageScrap = GuideBookManager.FIRST_PAGE;
            this.colour = 0;
            this.maxLineLength = 150;
            this.lineGap = 9;
            this.dropShadow = false;
            this.textWrapping = true;
        }

        /**
         * Creates a new GuideText builder for a given translation key
         * @param translationKey The key for the translation, not the translation itself
         * @return new GuideText builder
         */
        public static Text of(String translationKey) {
            return new Text(translationKey);
        }

        /**
         * The colour for the text to display, defaults to 0
         * @param colour the colour value
         * @return
         */
        public Text textColour(int colour) {
            this.colour = colour;
            return this;
        }

        /**
         * Max characters on a line, defaults to 150 (fills a page left to right)
         * @param length number of characters
         * @return this
         */
        public Text maxLineLength(int length) {
            this.maxLineLength = length;
            return this;
        }

        /**
         * The gap between each line, defaults to 9
         * @param gap the number of pixels
         * @return this
         */
        public Text lineGap(int gap) {
            this.lineGap = gap;
            return this;
        }

        /**
         * Enables dropShadow for the text
         * @return this
         */
        public Text dropShadow() {
            this.dropShadow = true;
            return this;
        }

        /**
         * Stops the text automatically wrapping around to a new line at max character limit
         * @return this
         */
        public Text disableWrapping() {
            this.textWrapping = false;
            return this;
        }

        /**
         * Sets the position the element appears on the page
         * @param x the x offset from left of the book
         * @param y the y offset from top of the book
         * @return this
         */
        public Text position(int x, int y) {
            this.position = new ElementPosition(x, y);
            return this;
        }

        /**
         * Sets the page scrap required to unlock this element. Will be obfuscated if not unlocked
         * @param scrap The id for the page scrap
         * @return this
         */
        public Text setRequiredScrap(ResourceLocation scrap) {
            this.pageScrap = scrap;
            return this;
        }

        public GuideText build() {
            return new GuideText(
                    translation,
                    new TextExtras(
                            pageScrap, colour, maxLineLength, lineGap, dropShadow, textWrapping
                    ),
                    position
            );
        }
    }

    public static class TextList {
        private List<String> entries;
        private ElementPosition position;
        private ResourceLocation pageScrap;
        private int maxRows;
        private int rowGap;
        private int columnGap;
        private String bulletPoint;
        private boolean dropShadow;
        private int textColour;

        private TextList() {
            this.entries = new ArrayList<>();
            this.position = ElementPosition.getDefault();
            this.pageScrap = GuideBookManager.FIRST_PAGE;
            this.maxRows = 0;
            this.rowGap = 20;
            this.columnGap = 45;
            this.bulletPoint = "▪";
            this.dropShadow = false;
            this.textColour = 0;
        }

        /**
         * Creates a new TextList builder
         * @return new TextList Builder
         */
        public static TextList of() {
            return new TextList();
        }

        /**
         * Adds a new entry to the list
         * @param text The list entry
         * @return this
         */
        public TextList addEntry(String text) {
            this.entries.add(text);
            return this;
        }

        /**
         * Sets the position the element appears on the page
         * @param x the x offset from left of the book
         * @param y the y offset from top of the book
         * @return this
         */
        public TextList position(int x, int y) {
            this.position = new ElementPosition(x, y);
            return this;
        }

        /**
         * Sets the page scrap required to unlock this element. Will be obfuscated if not unlocked
         * @param scrap The id for the page scrap
         * @return this
         */
        public TextList setRequiredScrap(ResourceLocation scrap) {
            this.pageScrap = scrap;
            return this;
        }

        /**
         * The maximum number of rows to be displayed, defaults to 0 (unlimited)
         * @param max the number of rows
         * @return this
         */
        public TextList maxRows(int max) {
            this.maxRows = max;
            return this;
        }

        /**
         * The gap between each row, defaults to 20
         * @param gap number of pixels
         * @return this
         */
        public TextList rowGap(int gap) {
            this.rowGap = gap;
            return this;
        }

        /**
         * Gap between columns, defaults to 45
         * @param gap number of pixels
         * @return this
         */
        public TextList columnGap(int gap) {
            this.columnGap = gap;
            return this;
        }

        /**
         * The String to be used for a bullet point, defaults to "▪"
         * @param bullet the bullet point to be used
         * @return this
         */
        public TextList bulletPoint(String bullet) {
            this.bulletPoint = bullet;
            return this;
        }

        /**
         * Enables drop shadow on the text
         * @return this
         */
        public TextList dropShadow() {
            this.dropShadow = true;
            return this;
        }

        /**
         * Sets the colour of the text, defaults to black
         * @param colour the colour value
         * @return this
         */
        public TextList textColour(int colour) {
            this.textColour = colour;
            return this;
        }

        public GuideTextList build() {
            return new GuideTextList(
                    entries,
                    new TextListExtras(
                            pageScrap,
                            maxRows,
                            rowGap,
                            columnGap,
                            dropShadow,
                            textColour,
                            bulletPoint
                    ), position
            );
        }
    }
}

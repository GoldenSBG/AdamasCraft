package ch.goldensbg.adamasCraft.menu.item;

import ch.goldensbg.adamasCraft.AdamasCraft;
import com.mojang.authlib.GameProfile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.attribute.Attribute;
import org.bukkit.persistence.PersistentDataType;

public class ItemBuilder {
    private ItemStack stack;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public ItemBuilder(Material mat) {
        this.stack = new ItemStack(mat);
    }

    public ItemMeta getItemMeta() {
        return this.stack.getItemMeta();
    }

    public ItemBuilder setColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta)this.stack.getItemMeta();
        assert meta != null;
        meta.setColor(color);
        setItemMeta((ItemMeta)meta);
        return this;
    }

    public ItemBuilder setGlow(boolean glow) {
        if (glow) {
            addEnchant(Enchantment.KNOCKBACK, 1);
            addItemFlag(ItemFlag.HIDE_ENCHANTS);
        } else {
            ItemMeta meta = getItemMeta();
            for (Enchantment enchantment : meta.getEnchants().keySet())
                meta.removeEnchant(enchantment);
        }
        return this;
    }

    public ItemBuilder addBannerPattern(Pattern pattern) {
        if (this.stack.getItemMeta() instanceof BannerMeta) {
            BannerMeta meta = (BannerMeta) this.stack.getItemMeta();
            meta.addPattern(pattern);
            setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setBannerPatterns(List<Pattern> patterns) {
        if (this.stack.getItemMeta() instanceof BannerMeta) {
            BannerMeta meta = (BannerMeta) this.stack.getItemMeta();
            meta.setPatterns(patterns);
            setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setBannerPattern(int layer, Pattern pattern) {
        BannerMeta meta = (BannerMeta)this.stack.getItemMeta();
        assert meta != null;
        meta.setPattern(layer, pattern);
        setItemMeta((ItemMeta)meta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.stack.setAmount(amount);
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta meta) {
        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setHead(String owner) {
        SkullMeta meta = (SkullMeta)this.stack.getItemMeta();
        assert meta != null;
        meta.setOwner(owner);
        setItemMeta((ItemMeta)meta);
        return this;
    }

    public ItemBuilder setHeadTexture(String value) {
        SkullMeta skullMeta = (SkullMeta) getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), ""); // Profilname auf leeren String setzen
        //profile.getProperties().put("textures", new Property("textures", value));
        try {
            Method setProfileMethod = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            setProfileMethod.setAccessible(true);
            setProfileMethod.invoke(skullMeta, profile);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        setItemMeta(skullMeta);
        return this;
    }


    public ItemBuilder setHeadTexture(String value, UUID uuid) {
        SkullMeta skullMeta = (SkullMeta)getItemMeta();
        GameProfile profile = new GameProfile(uuid, null);
        //profile.getProperties().put("textures", new Property("textures", value));
        Field profilefield = null;
        try {
            profilefield = skullMeta.getClass().getDeclaredField("profile");
            profilefield.setAccessible(true);
            profilefield.set(skullMeta, profile);
        } catch (NoSuchFieldException|IllegalAccessException ex) {
            ex.printStackTrace();
        }
        setItemMeta((ItemMeta)skullMeta);
        return this;
    }

    public ItemBuilder setName(String displayname) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(displayname);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setItemStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        ItemMeta meta = getItemMeta();
        meta.setLore(lore);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = getItemMeta();
        meta.setLore(Arrays.asList(lore));
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(lore);
        ItemMeta meta = getItemMeta();
        meta.setLore(loreList);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setMiniMessageLore(String... lore) {
        List<Component> loreComponents = new ArrayList<>();

        for (String line : lore) {
            loreComponents.add(miniMessage.deserialize(line));
        }
        ItemMeta meta = getItemMeta();
        if (meta != null) {
            meta.lore(loreComponents);
        }
        return this;
    }


    public ItemBuilder setCustomModelData(Integer modelData) {
        ItemMeta meta = getItemMeta();
        meta.setCustomModelData(modelData);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLore(String line) {
        ItemMeta im = this.stack.getItemMeta();
        List<String> lore = new ArrayList<>();
        assert im != null;
        if (im.hasLore())
            lore = new ArrayList<>(Objects.<Collection<? extends String>>requireNonNull(im.getLore()));
        lore.add(line);
        im.setLore(lore);
        this.stack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLore(String line, int pos) {
        ItemMeta im = this.stack.getItemMeta();
        assert im != null;
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        this.stack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = getItemMeta();
        meta.addEnchant(enchantment, level, true);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(new ItemFlag[] { flag });
        setItemMeta(meta);
        return this;
    }
    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier){
        ItemMeta meta = getItemMeta();
        meta.addAttributeModifier(attribute, attributeModifier);
        return this;
    }
    public ItemBuilder addPersistentDataContainer(String namespacedKey, PersistentDataType persistentDataType, Object object) {
        ItemMeta meta = getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(new NamespacedKey(AdamasCraft.getInstance(), namespacedKey), persistentDataType, object);
            setItemMeta(meta);
        } else {
            System.out.println("ItemMeta is null, cannot add persistent data.");
        }
        return this;
    }

    public ItemStack build() {
        return this.stack;
    }
}
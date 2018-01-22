package me.koenn.bits.util.registry;

public interface RegistryKey<T> {

    String getKey(T object);
}

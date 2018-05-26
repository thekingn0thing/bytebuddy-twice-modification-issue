package com.github.thekingnothing.bytebuddy;


import com.github.thekingnothing.bytebuddy.transfromers.MakeMarkerInterfaceConstructorNonPublicTransformer;
import com.github.thekingnothing.bytebuddy.transfromers.MakePublicTransformer;
import com.github.thekingnothing.bytebuddy.transfromers.Transformer;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator.ForClassLoader;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.pool.TypePool;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class TransformTest {
    
    private TypePool typePool;
    
    @Before
    public void setUp() {
        typePool = TypePool.Default.ofClassPath();
    }
    
    @Test
    public void should_make_marker_constructor_private() {
        
        final Class<?> original = SomeClassToModifyWithConstructors.class;
        
        final Class<?> loaded = transformAndLoad(original, new MakeMarkerInterfaceConstructorNonPublicTransformer());
        
        final Constructor<?>[] constructors = loaded.getConstructors();
        
        assertThat(constructors)
            .hasSize(0);
    }
    
    @Test
    public void should_make_all_constructor_public_except_marker() {
        
        final Class<?> original = SomeClassToModifyWithConstructors.class;
        
        final Class<?> loaded = transformAndLoad(original, new MakePublicTransformer(), new MakeMarkerInterfaceConstructorNonPublicTransformer());
        
        final Constructor<?>[] constructors = loaded.getConstructors();
        
        assertThat(constructors)
            .hasSize(2);
    }
    
    private Class<?> transformAndLoad(final Class<?> original,
                                      final Transformer... transformers) {
        final TypeDescription typeDefinition = typePool.describe(original.getName()).resolve();
        Builder<Object> builder = new ByteBuddy()
                                      .rebase(
                                          typeDefinition,
                                          ForClassLoader.ofClassPath()
                                      );
        
        for (Transformer transformer : transformers) {
            builder = transformer.transform(builder);
        }
        final URL[] urls = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
        return builder.make()
                      .load(new URLClassLoader(urls))
                      .getLoaded();
    }
}

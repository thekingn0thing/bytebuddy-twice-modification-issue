package com.github.thekingnothing.bytebuddy.transfromers;

import com.github.thekingnothing.bytebuddy.MarkerInterface;
import net.bytebuddy.asm.ModifierAdjustment;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

import static net.bytebuddy.matcher.ElementMatchers.isPublic;

public class MakeMarkerInterfaceConstructorNonPublicTransformer implements Transformer {
    
    public Builder transform(final Builder builder) {
        final TypePool typePool = TypePool.Default.ofClassPath();
        final Generic parameter = typePool.describe(MarkerInterface.class.getName())
                                          .resolve()
                                          .asGenericType();
        
        return builder
                   .visit(
                       new ModifierAdjustment().withConstructorModifiers(
                           isPublic().and(ElementMatchers.takesGenericArgument(0, parameter)),
                           Visibility.PRIVATE
                       )
                   );
    }
}

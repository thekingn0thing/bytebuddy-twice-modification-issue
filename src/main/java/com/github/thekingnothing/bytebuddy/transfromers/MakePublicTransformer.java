package com.github.thekingnothing.bytebuddy.transfromers;


import net.bytebuddy.asm.ModifierAdjustment;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType.Builder;

public class MakePublicTransformer implements Transformer {
    public Builder transform(final Builder builder) {
        return builder.visit(new ModifierAdjustment()
                                 .withConstructorModifiers(Visibility.PUBLIC)
        );
    }
}

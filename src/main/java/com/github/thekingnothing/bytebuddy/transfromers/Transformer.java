package com.github.thekingnothing.bytebuddy.transfromers;

import net.bytebuddy.dynamic.DynamicType.Builder;

public interface Transformer {
    
    public Builder transform(Builder builder);
    
}

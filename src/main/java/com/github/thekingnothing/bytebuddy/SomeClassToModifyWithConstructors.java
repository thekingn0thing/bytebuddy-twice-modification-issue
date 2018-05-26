package com.github.thekingnothing.bytebuddy;


public class SomeClassToModifyWithConstructors extends ParentClass {
    
    private SomeClassToModifyWithConstructors() {
        super(new Object());
    }
    
    protected SomeClassToModifyWithConstructors(Object o) {
        super(o);
    }
    
    public SomeClassToModifyWithConstructors(MarkerInterface p) {
        super(p);
    }
    
}

package com.example.base.application;

public abstract class UseCase<I, O> {

    public abstract O execute(I input);

}

package com.example.base.application;

public abstract class UseCase<Input, Output> {

    public abstract Output execute(Input input);

}

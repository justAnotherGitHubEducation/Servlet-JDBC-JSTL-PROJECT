package main.com.simpleonlineshop.mapper;

public interface Mapper<F, T> {

    public T mapFrom(F object);
}

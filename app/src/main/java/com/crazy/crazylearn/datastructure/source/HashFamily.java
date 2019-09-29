package com.crazy.crazylearn.datastructure.source;

public interface HashFamily<AnyType>
{
    int hash(AnyType x, int which);
    int getNumberOfFunctions();
    void generateNewFunctions();
}

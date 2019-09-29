package com.crazy.crazylearn.manager;

public class WindowManager {

    /***
     *
     *  1、LayoutInflater inflate = getLayoutInflater()
     *  2、LayoutInflater inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
     *  3、LayoutInflater inflater = LayoutInflater.from(context)
     *
     *  inflater.inflate(resid,root,attachToRoot)
     *
     *  参数说明:
     *  root != null,attachToRoot == true
     *      表示将resource指定的布局添加到root中，添加的过程中resource所指定的的布局的根节点的各个属性都是有效的
     *  root != null,attachToRoot == false
     *      表示不将第一个参数所指定的View添加到root中,
     *      root会协助插入布局的根节点生成布局参数
     *  root == null
     *      不论attachToRoot为true还是为false，显示效果都是一样的。当root为null表示不需要将第一个参数所指定的布局添加到任何容器中，
     *      同时也表示没有任何容器来来协助第一个参数所指定布局的根节点生成布局参数
     *
     *
     *
     * Activity、Window、PhoneWindow、DecorView
     *
     *    Activity
     *      PhoneWindow
     *          DecorView
     *              TitleView
     *              ContentView
     *
     *
     */
}

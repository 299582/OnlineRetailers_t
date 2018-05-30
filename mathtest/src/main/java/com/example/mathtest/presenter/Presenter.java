package com.example.mathtest.presenter;

public class Presenter implements presenterlistener{



    public void setTextColor(int in){
        if(in % 3 == 1){
            if(setcolor != null){
                setcolor.setcolorred();
            }
        }else if(in % 3 == 2){
            if(setcolor != null) {
                setcolor.setcolorgreen();
            }
        }else {
            if(setcolor != null){
                setcolor.setcolorblue();
            }
        }
    }


    private final presenterlistener setcolor;

    public Presenter(presenterlistener setcolor){

        this.setcolor = setcolor;
    }



    @Override
    public void setcolorred() {

    }

    @Override
    public void setcolorgreen() {

    }

    @Override
    public void setcolorblue() {

    }
}

package com.mediaymedia.info.model;


public class IndiceSeccionOrigenImpl extends IndiceSeccionImpl {
    private int ordenAnterior;

    public IndiceSeccionOrigenImpl(SeccionBase seccionBase, EntidadBase.tipo tipo) {
        super(seccionBase, tipo);
    }

    public int getOrdenAnterior() {
        return ordenAnterior;
    }

    public void setOrdenAnterior(int ordenAnterior) {
        this.ordenAnterior = ordenAnterior;
    }
}

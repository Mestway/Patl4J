package com.mediaymedia.commons.io.image;

/**
 *
 * User: juan
 * Date: 30-may-2007
 * Time: 18:09:07
 * To change this template use File | Settings | File Templates.
 */
public class TipoLimite {
    Limite limitePrincipal;
    Limite limiteSecundario;


    public TipoLimite(Proporcion principal, int medidaPrincipal, int medidaSecundaria) {
        this.limitePrincipal = new Limite(principal, medidaPrincipal);
        this.limiteSecundario = new Limite(Proporcion.dameContrario(principal), medidaSecundaria);

    }


    public Limite getLimitePrincipal() {
        return limitePrincipal;
    }

    public Limite getLimiteSecundario() {
        return limiteSecundario;
    }
}

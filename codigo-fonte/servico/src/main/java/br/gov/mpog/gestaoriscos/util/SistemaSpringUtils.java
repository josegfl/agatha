package br.gov.mpog.gestaoriscos.util;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Classe Util para obter {@link ApplicationContext} do Spring onde não podemos realizar o {@link org.springframework.beans.factory.annotation.Autowired}
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SistemaSpringUtils implements ApplicationContextAware {

    private ApplicationContext context;
    private static SistemaSpringUtils instance;
    private final String sincrono = Boolean.TRUE.toString();

    /**
     * Obter o valor do atributo <code>instance</code> da classe.
     *
     * @return O atributo <code>instance</code>.
     */
    public static SistemaSpringUtils getInstance() {
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        synchronized (sincrono) {
            instance = ObjectUtils.defaultIfNull(instance, context.getBean(SistemaSpringUtils.class));
        }
    }

    /**
     * Obter um Bean sem precisar fazer {@link org.springframework.beans.factory.annotation.Autowired}
     *
     * @param clazz Classe do BEAN
     * @param <T>   Tipificador Genérico
     * @return Bean Spring
     */
    public <T> T getBean(@NotNull Class<T> clazz) {
        synchronized (sincrono) {
            return clazz.cast(context.getBean(clazz));
        }
    }
}

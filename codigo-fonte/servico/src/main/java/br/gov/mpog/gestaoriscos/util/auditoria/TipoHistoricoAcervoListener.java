package br.gov.mpog.gestaoriscos.util.auditoria;

import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import br.gov.mpog.gestaoriscos.servico.UsuarioService;
import br.gov.mpog.gestaoriscos.util.SistemaSpringUtils;
import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listener do Hibernate Envers para auditoria do projeto gestão riscos.
 */
public class TipoHistoricoAcervoListener implements EntityTrackingRevisionListener {

    private static final Logger LOGGER = Logger.getLogger(TipoHistoricoAcervoListener.class.getName());

    /**
     * Comentário para o SONAR
     *
     * @param entityClass
     * @param entityName
     * @param entityId
     * @param revisionType
     * @param revisionEntity
     */
    @Override
    public void entityChanged(Class entityClass, String entityName, Serializable entityId, RevisionType revisionType, Object revisionEntity) {
        LOGGER.log(Level.WARNING, "Método não implementado");
    }

    @Override
    public void newRevision(Object revisionEntity) {
        UsuarioDTO usuarioDTO = recuperarDadosUsuarioLogado();
        String enderecoIpCliente = recuperarEnderecoIpCliente();
        HistoricoAcervoAuditoria lAud = (HistoricoAcervoAuditoria) revisionEntity;
        lAud.setNoUsuario(usuarioDTO.getNome());
        lAud.setNuCpfUsuario(usuarioDTO.getCpf());
        lAud.setNuIpUsuario(enderecoIpCliente);
    }

    private UsuarioDTO recuperarDadosUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        String cpf = principal.getUsername();

        UsuarioService usuarioService = SistemaSpringUtils.getInstance().getBean(UsuarioService.class);
        return usuarioService.findByCpf(cpf);
    }

    private String recuperarEnderecoIpCliente() {
        final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        final HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        return getIpCliente(httpServletRequest, "x-forwarded-for");
    }

    private String getIpCliente(HttpServletRequest httpServletRequest, String headerParametrizado) {
        if (headerParametrizado != null && !headerParametrizado.isEmpty()) {
            for (String header : Arrays.asList(headerParametrizado.split(","))) {
                String ip = httpServletRequest.getHeader(header);
                if (ip != null) {
                    return ip;
                }
            }
        }
        return httpServletRequest.getRemoteAddr();
    }

}

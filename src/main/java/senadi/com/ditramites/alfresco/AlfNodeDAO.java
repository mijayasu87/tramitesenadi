/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.alfresco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

/**
 *
 * @author michael
 */
public class AlfNodeDAO extends DAOAbstractAlf<AlfNode> {

    public AlfNodeDAO(AlfNode t) {
        super(t);
    }

    @Override
    public List<AlfNode> buscarTodos() {
        try {
            Query query = this.getEntityManager().createQuery("Select a from AlfNode a");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public String getChildNodeNameByUuid(String uuid) {
        Map<String, String> namesByUuid = getChildNodeNamesByUuids(Arrays.asList(uuid));
        return namesByUuid.getOrDefault(uuid, "");
    }

    public List<String> getUuidsByChildNodeName(String childNodeName) {
        return getUuidsByChildNodeNames(Arrays.asList(childNodeName)).getOrDefault(childNodeName, new ArrayList<>());
    }

    public Map<String, String> getChildNodeNamesByUuids(List<String> uuids) {
        Map<String, String> childNodeNamesByUuid = new LinkedHashMap<>();
        if (uuids == null || uuids.isEmpty()) {
            return childNodeNamesByUuid;
        }

        try {
            Query query = this.getEntityManager().createNativeQuery(
                    "SELECT alf.uuid, aca.child_node_name "
                    + "FROM alf_node AS alf "
                    + "INNER JOIN alf_child_assoc AS aca ON aca.child_node_id = alf.id "
                    + "WHERE alf.uuid IN (" + buildInClause(uuids.size()) + ")"
            );

            for (int i = 0; i < uuids.size(); i++) {
                query.setParameter(i + 1, uuids.get(i));
            }

            List<?> result = query.getResultList();
            for (Object value : result) {
                Object[] row = (Object[]) value;
                if (row[0] != null && row[1] != null) {
                    childNodeNamesByUuid.put(row[0].toString(), row[1].toString());
                }
            }
            return childNodeNamesByUuid;
        } finally {
            this.getEntityManager().close();
        }
    }

    public Map<String, List<String>> getUuidsByChildNodeNames(List<String> childNodeNames) {
        Map<String, List<String>> uuidsByChildNodeName = new LinkedHashMap<>();
        if (childNodeNames == null || childNodeNames.isEmpty()) {
            return uuidsByChildNodeName;
        }

        for (String childNodeName : childNodeNames) {
            uuidsByChildNodeName.put(childNodeName, new ArrayList<>());
        }

        try {
            Query query = this.getEntityManager().createNativeQuery(
                    "SELECT aca.child_node_name, alf.uuid "
                    + "FROM alf_node AS alf "
                    + "INNER JOIN alf_child_assoc AS aca ON aca.child_node_id = alf.id "
                    + "WHERE aca.child_node_name IN (" + buildInClause(childNodeNames.size()) + ")"
            );

            for (int i = 0; i < childNodeNames.size(); i++) {
                query.setParameter(i + 1, childNodeNames.get(i));
            }

            List<?> result = query.getResultList();
            for (Object value : result) {
                Object[] row = (Object[]) value;
                if (row[0] != null && row[1] != null) {
                    uuidsByChildNodeName.computeIfAbsent(row[0].toString(), key -> new ArrayList<>()).add(row[1].toString());
                }
            }
            return uuidsByChildNodeName;
        } finally {
            this.getEntityManager().close();
        }
    }

    private String buildInClause(int size) {
        StringBuilder inClause = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                inClause.append(',');
            }
            inClause.append('?');
        }
        return inClause.toString();
    }

}

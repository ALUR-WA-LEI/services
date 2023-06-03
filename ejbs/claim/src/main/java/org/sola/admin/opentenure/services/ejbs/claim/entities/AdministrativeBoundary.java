package org.sola.admin.opentenure.services.ejbs.claim.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.sola.services.common.repository.AccessFunctions;
import org.sola.services.common.repository.entities.AbstractVersionedEntity;

@Table(schema = "opentenure", name = "administrative_boundary")
public class AdministrativeBoundary extends AbstractVersionedEntity {

    @Id
    @Column
    private String id;
    @Column(name = "name", updatable = false)
    private String name;
    @Column(name = "type_code")
    private String typeCode;
    @Column(name = "authority_name")
    private String authorityName;
    @Column(name = "authority_code")
    private String authorityCode;
    @Column(name = "parent_id")
    private String parentId;
    @Column(name = "recorder_name")
    private String recorderName;
    @Column(name = "status_code")
    private String statusCode;
    @Column(name = "geom")
    @AccessFunctions(onSelect = "ST_AsText(geom)",
            onChange = "ST_GeomFromText(#{geom})")
    private String geom;

    public static final String PARAM_STATUS = "status";
    public static final String PARAM_TYPE_CODE = "type_code";
    
    public static final String QUERY_BY_TYPE_AND_STATUS = "SELECT id, name, type_code, authority_name, authority_code, parent_id, recorder_name, status_code, ST_AsText(geom) as geom, rowversion, change_user, rowidentifier \n"
            + "FROM opentenure.administrative_boundary \n"
            + "WHERE (type_code = #{" + PARAM_TYPE_CODE + "} or #{" + PARAM_TYPE_CODE + "} = '') and \n"
            + "(status_code = #{" + PARAM_STATUS + "} or #{" + PARAM_STATUS + "} = '')";
    public static final String QUERY_SELECT_APPROVED = "WITH RECURSIVE all_administrative_boundaries AS (\n"
            + " SELECT id, name, type_code, authority_name, authority_code, parent_id, recorder_name, status_code, ST_AsText(geom) as geom, rowversion, change_user, rowidentifier, 1 as level, array[ROW_NUMBER() OVER (ORDER BY name)] AS path \n"
            + " FROM opentenure.administrative_boundary \n"
            + " WHERE parent_id is null and status_code = 'approved' \n"
            + "UNION \n"
            + " SELECT b.id, b.name, b.type_code, b.authority_name, b.authority_code, b.parent_id, b.recorder_name, b.status_code, ST_AsText(b.geom) as geom, b.rowversion, b.change_user, b.rowidentifier, ab.level + 1 as level, ab.path || (ROW_NUMBER() OVER (ORDER BY b.name)) AS path \n"
            + " FROM opentenure.administrative_boundary b inner join all_administrative_boundaries ab on b.parent_id = ab.id \n"
            + " WHERE b.status_code = 'approved' \n"
            + ")\n"
            + "SELECT id, name, type_code, authority_name, authority_code, parent_id, recorder_name, status_code, geom, rowversion, change_user, rowidentifier \n"
            + "FROM all_administrative_boundaries \n"
            + "ORDER BY path, level;";
    
    public static final String PARAM_BOUNDARY_NAME = "boundaryName";
    public static final String WHERE_BY_BOUNDARY_NAME = "name = #{ " + PARAM_BOUNDARY_NAME + "}";
    
    public static final String PARAM_CUSTOM_SRID = "customSrid";
    public static final String PARAM_BOUNDARY_ID = "boundaryId";
    public static final String QUERY_GET_BY_ID = "select id, name, type_code, authority_name, authority_code, parent_id, recorder_name, status_code, " 
            + "st_astext(case when coalesce(#{ " + PARAM_CUSTOM_SRID + "},0) = 0 then geom else st_transform(st_setsrid(geom,4326),#{ " + PARAM_CUSTOM_SRID + "}) end) as geom, "
            + "rowversion, change_user, rowidentifier "
            + "FROM opentenure.administrative_boundary "
            + "where id = #{ " + PARAM_BOUNDARY_ID + "}";

    public AdministrativeBoundary() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRecorderName() {
        return recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }
}

package pe.edu.utp.javawebregionslist.models;

import java.sql.Connection;
import java.util.List;

public class HrService {
    private Connection connection;
    private RegionsEntity regionsEntity;

    private Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    protected RegionsEntity getRegionsEntity() {
        if(getConnection() != null) {
            if(regionsEntity == null) {
                regionsEntity = new RegionsEntity();
                regionsEntity.setConnection(getConnection());
            }
        }
        return regionsEntity;
    }

    public List<Region> findAllRegions() {
        return getRegionsEntity() != null ?
                getRegionsEntity().findAll() : null;
    }

    public Region findRegionById(int id) {
        return getRegionsEntity() != null ?
                getRegionsEntity().findById(id) : null;
    }

    public Region findRegionByName(String name) {
        return getRegionsEntity() != null ?
                getRegionsEntity().findByName(name) : null;
    }

    public Region createRegion(String name) {
        return getRegionsEntity() != null ?
                getRegionsEntity().create(name) : null;
    }

    public boolean deleteRegion(int id) {
        return getRegionsEntity() != null ?
                getRegionsEntity().delete(id) : false;
    }

    public boolean updateRegion(Region region) {
        return getRegionsEntity() != null ?
                getRegionsEntity().update(region) : false;
    }
}

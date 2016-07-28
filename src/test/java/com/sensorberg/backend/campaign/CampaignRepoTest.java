package com.sensorberg.backend.campaign;

import com.sensorberg.backend.Application;
import com.sensorberg.backend.beacon.BeaconDoc;
import com.sensorberg.backend.beacon.BeaconRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.List;

import static com.sensorberg.backend.util.UuidUtil.newUuid;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true, value = "mongo.url=mongodb://localhost/admin")
//@WebIntegrationTest(randomPort = true, value = "mongo.url=<your DocumentDB connection string>")
public class CampaignRepoTest {
    @Autowired
    private BeaconRepo beaconRepo;

    @Autowired
    private CampaignRepo repo;

    private String validBeaconId;
    private String validBeacon2Id;
    private String campaignId;

    @Before
    public void setUp() throws Exception {
        // clean up DB:
        beaconRepo.deleteAll();
        repo.deleteAll();

        createTestBeacons();
        createTestCampaign();
    }

    @Test
    public void testGetCampaign() throws Exception {
        CampaignDoc campaignDoc = repo.findValidById(campaignId);
        assertNotNull("The campaign shouldn't be null.", campaignDoc);
        assertEquals("Campaign ID doesn't match.", campaignId, campaignDoc.getId());
        assertEquals("Campaign name doesn't match.", "My Awesome Campaign", campaignDoc.getName());
        assertEquals("Campaign owner doesn't match.", "myCompany", campaignDoc.getOwner());
        assertEquals("Campaign updated by doesn't match.", "admin", campaignDoc.getUpdatedBy());
        assertTrue("Campaign can't be created in the future.", Instant.now().plusNanos(1).isAfter(campaignDoc.getCreatedAt()));
        assertTrue("Campaign can't be updated in the future.", Instant.now().plusNanos(1).isAfter(campaignDoc.getUpdatedAt()));

        List<BeaconDoc> beacons = campaignDoc.getBeacons();
        assertNotNull("The list of campaign beacons shouldn't be null.", beacons);
        assertEquals("The list of campaign beacons should contain 1 beacon.", 1, beacons.size());
        BeaconDoc beaconDoc = beacons.get(0);
        assertNotNull("The campaign beacon shouln't be null.", beaconDoc);
        assertEquals("Beacon ID doesn't match.", validBeaconId, beaconDoc.getId());
        assertEquals("Beacon name doesn't match.", "Valid Beacon", beaconDoc.getName());
        assertEquals("Beacon owner doesn't match.", "myCompany", beaconDoc.getOwner());
        assertEquals("Beacon updated by doesn't match.", "admin", beaconDoc.getUpdatedBy());
        assertTrue("Beacon can't be created in the future.", Instant.now().plusNanos(1).isAfter(beaconDoc.getCreatedAt()));
        assertTrue("Beacon can't be updated in the future.", Instant.now().plusNanos(1).isAfter(beaconDoc.getUpdatedAt()));
    }

    @Test
    public void testUpdateCampaign() throws Exception {
        CampaignDoc campaignDoc = repo.findValidById(campaignId);
        BeaconDoc tmpBeaconDoc1 = new BeaconDoc();
        tmpBeaconDoc1.setId(validBeaconId).setName("tmpBeacon1");
        BeaconDoc tmpBeaconDoc2 = new BeaconDoc();
        tmpBeaconDoc2.setId(validBeacon2Id).setName("tmpBeacon2");
        campaignDoc.setBeacons(asList(tmpBeaconDoc1, tmpBeaconDoc2));
        repo.save(campaignDoc);

        campaignDoc = repo.findValidById(campaignId);
        assertNotNull("The campaign shouldn't be null.", campaignDoc);
        assertEquals("Campaign ID doesn't match.", campaignId, campaignDoc.getId());
        assertEquals("Campaign name doesn't match.", "My Awesome Campaign", campaignDoc.getName());

        List<BeaconDoc> beacons = campaignDoc.getBeacons();
        assertNotNull("The list of campaign beacons shouldn't be null.", beacons);
        assertEquals("The list of campaign beacons should contain 2 beacons.", 2, beacons.size());
        BeaconDoc beaconDoc = beacons.get(0);
        assertNotNull("The campaign beacon shouln't be null.", beaconDoc);
        assertEquals("Beacon ID doesn't match.", validBeaconId, beaconDoc.getId());
        assertEquals("Beacon name doesn't match.", "Valid Beacon", beaconDoc.getName());
        beaconDoc = beacons.get(1);
        assertNotNull("The campaign beacon shouln't be null.", beaconDoc);
        assertEquals("Beacon ID doesn't match.", validBeacon2Id, beaconDoc.getId());
        assertEquals("Beacon name doesn't match.", "Valid Beacon 2", beaconDoc.getName());
    }

    private void createTestBeacons() throws Exception {
        BeaconDoc validBeacon = new BeaconDoc();
        validBeacon.setUpdatedBy("admin")
                .setDeleted(false)
                .setOwner("myCompany")
                .setId(newUuid())
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now())
                .setName("Valid Beacon");
        beaconRepo.save(validBeacon);
        validBeaconId = validBeacon.getId();
        BeaconDoc validBeacon2 = new BeaconDoc();
        validBeacon2.setUpdatedBy("admin")
                .setDeleted(false)
                .setOwner("myCompany")
                .setId(newUuid())
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now())
                .setName("Valid Beacon 2");
        beaconRepo.save(validBeacon2);
        validBeacon2Id = validBeacon2.getId();
    }
    private void createTestCampaign() throws Exception {
        CampaignDoc campaignDoc = new CampaignDoc();
        campaignDoc.setUpdatedBy("admin")
                .setDeleted(false)
                .setOwner("myCompany")
                .setBeacons(singletonList(new BeaconDoc().setId(validBeaconId)))
                .setId(newUuid())
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now())
                .setName("My Awesome Campaign");
        repo.save(campaignDoc);
        campaignId = campaignDoc.getId();
    }
}

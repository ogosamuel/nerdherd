package com.example.nerdherd;

import com.example.nerdherd.Deprecated.Experiment_Deprecated;
import com.example.nerdherd.Deprecated.Profile;
import com.example.nerdherd.Deprecated.SearchController;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class testSearch {

    ArrayList<Experiment_Deprecated> resultList;
    ArrayList<Profile> userResultList;

    public Profile mockProfile() {
        return new Profile("test", "password", "email", "id", 1);
    }
    public Experiment_Deprecated mockExperiment() {
        return new Experiment_Deprecated(mockProfile(), "test", "status", "description", "type", 1, true, true, new ArrayList(), new ArrayList());
    }

    public void searchExperiment(String keyword, ArrayList<Experiment_Deprecated> showList) {
        SearchController mockController = new SearchController();
        mockController.searchExperiment(keyword, showList, resultList, new SearchController.ExperimentNoResultCallBack() {
            @Override
            public void onCallback(ArrayList<Experiment_Deprecated> itemList) {}
        }, new SearchController.ExperimentResultCallBack() {
            @Override
            public void onCallback(ArrayList<Experiment_Deprecated> itemList) {}
        }, new SearchController.ExperimentNoKeywordCallBack() {
            @Override
            public void onCallback(ArrayList<Experiment_Deprecated> itemList) {}
        });
    }

    public void searchProfile(String keyword, ArrayList<Profile> showList) {
        SearchController mockController = new SearchController();
        mockController.searchUser(keyword, showList, userResultList, new SearchController.UserNoResultCallBack() {
            @Override
            public void onCallback(ArrayList<Profile> itemList) {}
        }, new SearchController.UserResultCallBack() {
            @Override
            public void onCallback(ArrayList<Profile> itemList) {}
        }, new SearchController.UserNoKeywordCallBack() {
            @Override
            public void onCallback(ArrayList<Profile> itemList) {}
        });
    }

    @Test
    public void testSearchExperiment() {
        resultList = new ArrayList<>();
        resultList.clear();
        ArrayList<Experiment_Deprecated> showList = new ArrayList<>();
        Experiment_Deprecated mockExp = mockExperiment();

        searchExperiment("test", showList);
        assertFalse(resultList.contains(mockExp));
        resultList.clear();

        showList.add(mockExp);

        searchExperiment("test", showList);
        assertTrue(resultList.contains(mockExp));
        resultList.clear();

        searchExperiment("doesNotExist", showList);
        assertFalse(resultList.contains(mockExp));
        resultList.clear();

        searchExperiment("email", showList);
        assertTrue(resultList.contains(mockExp));
        resultList.clear();

        searchExperiment("TEsT", showList);
        assertTrue(resultList.contains(mockExp));
        resultList.clear();

        searchExperiment("status", showList);
        assertTrue(resultList.contains(mockExp));
        resultList.clear();

        Experiment_Deprecated secondExperimentDeprecated = new Experiment_Deprecated(mockProfile(), "second", "status", "description", "type", 1, true, true, new ArrayList(), new ArrayList());
        showList.add(secondExperimentDeprecated);

        searchExperiment("second", showList);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(secondExperimentDeprecated));
        assertFalse(resultList.contains(mockExp));
        resultList.clear();
    }

    @Test
    public void testSearchProfile() {
        userResultList = new ArrayList<>();
        ArrayList<Profile> showList = new ArrayList<>();
        Profile mockProf = mockProfile();

        searchProfile("test", showList);
        assertFalse(userResultList.contains(mockProf));
        userResultList.clear();

        showList.add(mockProf);

        searchProfile("test", showList);
        assertTrue(userResultList.contains(mockProf));
        userResultList.clear();

        searchProfile("doesNotExist", showList);
        assertFalse(userResultList.contains(mockProf));
        userResultList.clear();

        searchProfile("TeST", showList);
        assertTrue(userResultList.contains(mockProf));
        userResultList.clear();

        searchProfile("email", showList);
        assertTrue(userResultList.contains(mockProf));
        userResultList.clear();

        Profile secondProfile = new Profile("secondTest", "pass", "email", "id", 1);
        showList.add(secondProfile);

        searchProfile("second", showList);
        assertEquals(1, userResultList.size());
        assertTrue(userResultList.contains(secondProfile));
        assertFalse(userResultList.contains(mockProf));
        userResultList.clear();
    }
}

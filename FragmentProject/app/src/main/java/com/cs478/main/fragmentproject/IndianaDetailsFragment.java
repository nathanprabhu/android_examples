package com.cs478.main.fragmentproject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Varshini on 3/15/2016.
 */

//This class handles the web view or details fragment for Indiana
public class IndianaDetailsFragment extends Fragment {

    private WebView webPageItem = null;
    private int currentIndex = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This is to ensure that the state is saved across configuration changes.
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_details, container, false);
    }

    // Set up some information about the web view element
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webPageItem = (WebView) getActivity().findViewById(R.id.webPage);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    int getShownIndex() {
        return currentIndex;
    }

    // Show the web page of the place selected
    void showWebPage(int index) {
        if (index < 0 || index >= IndianaActivity.linksArray.length)
            return;
        currentIndex = index;
        webPageItem.getSettings().setJavaScriptEnabled(true);
        System.out.println(IndianaActivity.linksArray[currentIndex]);
        webPageItem.loadUrl(IndianaActivity.linksArray[currentIndex]);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

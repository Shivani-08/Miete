package com.example.shivani.miete;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment implements View.OnClickListener {
    Button buttonSend;
    EditText textTo;
    EditText textSubject;
    EditText textMessage;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        buttonSend = (Button) view.findViewById(R.id.buttonSe);
        textTo = (EditText)  view.findViewById(R.id.editTextTo);
        textSubject = (EditText)  view.findViewById(R.id.editTextSubject);
        textMessage = (EditText) view.findViewById(R.id.editTextMessage);
        textTo.setText("rent_miete@gmail.com");
        buttonSend.setOnClickListener(this);

        return view;
    }




    @Override
    public void onClick (View v){

        String to = textTo.getText().toString();
        String subject = textSubject.getText().toString();
        String message = textMessage.getText().toString();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
        //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

       getActivity().startActivity(Intent.createChooser(email, "Choose an Email client :"));

    }
}

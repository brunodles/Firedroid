# Firedroid - Making a chat app with firebase

In this tutorial we will make an chat app with firebase.

## Step 1 - Import firebase on `app/build.gradle`
In `dependencies` put this line below and then run *Gradle Sync*.
This will download firba base client api for your project.

		compile 'com.firebase:firebase-client-android:2.3.+'

### If you got some build error about duplicated files add these lines on your `packagingOptions` on the same file

        exclude 'META-INF/LICENSE'
        exclude 'META-INF/LICENSE-FIREBASE.txt'
        exclude 'META-INF/NOTICE'

## Step 2 - Add internet permission on your manifest

		<uses-permission android:name="android.permission.INTERNET" />

## Step 3 - Initialize firebase
Go to your *Application.java* and initialize firebase with `Firebase.setAndroidContext(this);`, in **onCreate()** method.
If you don't hava a Application.java, just initialize it on your first Activity.

## Step 4 - Create a helper for your firebase connection
This class will help you to access your firebase.

		public final class FirebaseHelper {

			private FirebaseHelper() {
			}

			public static Firebase get() {
				return new Firebase("https://<YOUR-FIREBASE-APP>.firebaseio.com/");
			}
		}

## Step 5 - Make Login

### Create login activity
Just create an activity with two `EditText` and two `Buttons`.
It should be like.

		Email
		Password
		Login
		Signin

Make a `clickListener` for login and signin.

### Let's create our login helper
This will manage logins and signups.
So, there's a lot of code here then I'll only show the main part of it.
They work with a listener, that will be called when we got some response.

#### To login
When user click on login call this

		firebase.authWithPassword(email, password, new AuthResultHandler());

#### To register
When user click on signup, call this

		firebase.createUser(email, password, new SignUpResultHandler());

#### Callback sample
The method name is diferent, but the idea is the same.

		class SignUpResultHandler implements Firebase.ResultHandler {

			@Override
			public void onSuccess() {
				// Signup with success, redirect to your chat activity
			}

			@Override
			public void onError(FirebaseError firebaseError) {
				// Error, may email already exist
			}
		}


## Step 6 - Let's create a Chat Activity

### Send message
To send a message is simple, we need to get you root firebase reference and move to **messages**.
Then we'll **push** one new reference, like a list.
Place the message on it.

		messages = firebaseRoot.child("messages");
		message = messages.push();
		message.child("author").setValue(firebaseRoot.getAuth().getUid());
		message.child("message").setValue(mMessage.getText().toString());

### Get the messages back
To get our messages back, we create a listener for **messages**.
		
		firebaseRoot.child("messages").addChildEventListener(new ChildEventListener() {
			@Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
				// when a new child is pushed it will apear here.
			}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
				// when a child is changed it will apear here
			}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
				// when a child is removed
			}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
				// when a child is moved to anoter position
			}

            @Override
            public void onCancelled(FirebaseError firebaseError) {
				// when the request is cacelled
			}
		}

### Create Item Layout
The most simple layout, don't even need to bre created because the default android layout already have a similar layout fot that. But we will put here just to personalize it. So it's just a `LinarLayout` with two `TextView`s. The first one for the author and the second one for the message.  
Later we can add a photo and/or timestamp here.

### Create one Adapter
Well you already know how to get the childs, now how to create a adapter?  
Just need to put one `addChildEventListener` for **messages** inside the adapter.
Start listeneing on adapter constructor.
Make that adapter have a `List` of **messages** and `String`.  
That String list will keep the keys for each object.  
Just to remember, this apdater will use the item layout above.

### Create Chat Layout
The chat layout is simple, we just need to make a `LinearLayout` with a `ListView`, `EditText` and a `Button`.
ListView will have show the messages.
EditText will let users write their messages to he others.
Button will send the message.

### Chat Activity
The activity must use the Chat Layout.
Use the adapter that we created on `listview`.
When click on send button call the send method we created above.

## Step 7 - Let's have fun
That's it!  
Just send to your friend a enjoy.  
Questions, suggestions and improvements are welcome.  

# TODOs
* Create a user profile.
* Timestamp on message




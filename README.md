# Firedroid - Making a chat app with firebase

In this tutorial we will make an chat app with firebase.

## step 1 - Import firebase on `app/build.gradle`
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

## Step 5 - Let's create our login helper
This will manage logins and signups.
So, there's a lot of code here then I'll only show the main part of it.
They work with a listener, that will be called when we got some response.

### To login
When user click on login call this

		firebase.authWithPassword(email, password, new AuthResultHandler());

### To register
When user click on signup, call this

		firebase.createUser(email, password, new SignUpResultHandler());

### Callback sample

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


## Step 6 - 





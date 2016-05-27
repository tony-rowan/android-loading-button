# Android Loading Button
Two simple buttons with loading states and animations for Android.

## Features
- Easy to install and use.
- Nice defaults
- Easy to customize
- Auto disables button when set to loading
- Auto selects disabled color
- Two default layouts and animations to choose from

## Installation
Install via gradle by adding the following dependency
```gradle
compile 'com.github.92tonywills:android-loading-button:0.1.0'
```

## Usage
Include a button in your xml layout
```xml
<!-- or com.github.tonywills.loadingbutton.HorizontalLoadingButton -->
<com.github.tonywills.loadingbutton.LoadingButton
    android:id="@+id/loading_button"
    android:layout_width="240dp"
    android:layout_height="120dp"
    <!-- Customization options -->
    style="@style/Widget.AppCompat.Button.Colored" />
```

The style attribute it not nessessary, but will make the button display like a default android button. 
On post Lollipop devices, this means it will appear with the Material ripple and elevation changes.
The button tints itself correctly with this style.

### Customisation
The following attributes are available for you to customize the look of the button

- `buttonBackgroundTint`: a colour resource
    - The normal colour of the button. A lighter version of this colour will be used for the disabled loadingButtonState when the button is loading.
- `loadingColor`: a colour resource
    - The colour of the progress indicator, on material supporting devices, this will be the pretty Android spinner
- `buttonText`: a string resource
    - The text to show

The `HorizontalLoadingButton` has all the previous attributes plus
- `buttonTextDefault`: a string resource
    - replaces `buttonText`
    - is the text to show when the view is not loading
- `buttonTextLoading`: a string resource
    - The text to show when the button is loading
- `loadingPosition`: either `left` or `right`
    - Which side to place the spinner

A fully customized button could look like this
```xml
<com.github.tonywills.loadingbutton.LoadingButton
    android:id="@+id/loading_button"
    android:layout_width="240dp"
    android:layout_height="120dp"
    app:buttonBackgroundTint="@color/primaryColor"
    app:loadingColor="@color/secondaryColor"
    app:buttonText="Go"
    style="@style/Widget.AppCompat.Button.Colored" />
```
Be sure to inculde `xmlns:app="http://schemas.android.com/apk/res-auto"` at the top of your file to access the custom attributes.

### Animations
The button includes a default animation to move the text out and the spinner in. To customise this animation, subclass the
button and override the `animateToState` method. See the library source to see an example animation.

### Two Variants
The only difference in the two variants (`LoadingButton` and `HorizontalLoadingButton`) is the layout. This means the animations
are different, too. Check out the [example app](sample) to see them in action.

## Example App
Take a look at the [example app](sample) to see how to use the button.

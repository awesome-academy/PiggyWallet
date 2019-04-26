package com.thailam.piggywallet.ui.walletdetail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

public class SwipeDismissTouchListener implements View.OnTouchListener {
    private final int ORIGINAL_STAT = 0;

    // Cached ViewConfiguration and system-wide constant values
    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private long mAnimationTime;
    // Fixed properties
    private View mView;
    private DismissCallbacks mCallbacks;
    private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero
    // Transient properties
    private float mDownX; // x-coord of finger touch down
    private float mDownY; // y-coord of finger touch down
    private boolean mSwiping;
    private VelocityTracker mVelocityTracker;
    private float mTranslationX;

    /**
     * Constructs a new swipe-to-dismiss touch listener for the given view.
     *
     * @param view      The view to make dismissible.
     * @param callbacks The callback to trigger when the user has indicated that she would like to
     *                  dismiss this view.
     */
    SwipeDismissTouchListener(View view, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(view.getContext());
        mSlop = vc.getScaledTouchSlop(); // Distance in pixels a touch can wander before we think the user is scrolling
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mAnimationTime = view.getContext().getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        mView = view;
        mCallbacks = callbacks;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        motionEvent.offsetLocation(mTranslationX, ORIGINAL_STAT);
        final int MIN_WIDTH_THRESHOLD = 2;
        if (mViewWidth < MIN_WIDTH_THRESHOLD) mViewWidth = mView.getWidth();
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                onActionDown(motionEvent);
                break;
            case MotionEvent.ACTION_UP:
                return onActionUp(motionEvent);
            case MotionEvent.ACTION_CANCEL:  // on cancel, reset everything
                onActionCancel();
                break;
            case MotionEvent.ACTION_MOVE:  // move the view accordingly to user touch
                return onActionMove(motionEvent);
        }
        return false;
    }

    private void onActionDown(MotionEvent motionEvent) {
        mDownX = motionEvent.getRawX();
        mDownY = motionEvent.getRawY();
        mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(motionEvent);
    }

    private boolean onActionUp(MotionEvent motionEvent) {
        if (mVelocityTracker == null) return false;
        final int VELOCITY_TRACKER_UNIT = 1000;
        mVelocityTracker.addMovement(motionEvent);
        mVelocityTracker.computeCurrentVelocity(VELOCITY_TRACKER_UNIT);
        float deltaX = motionEvent.getRawX() - mDownX;
        float velocityX = mVelocityTracker.getXVelocity();
        boolean dismiss = false;
        // action up logic
        if (velocityX > 0) { // only allow swipe to the right
            dismiss = isDismissed(deltaX, velocityX);
        }
        if (dismiss) { // animate dismiss when called
            animateDismiss();
            return true;
        } else if (mSwiping) { // return to original if move up when not fling/<half view width
            animateToOriginalPosition();
            return true;
        }
        resetVariables();
        return false;
    }

    private boolean isDismissed(float deltaX, float velocityX) {
        float absVelocityX = Math.abs(velocityX);
        float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
        if (deltaX > mViewWidth / 2 && mSwiping) {
            // dismiss dialog if swipe more than half screen
            return true;
        } else if (mMinFlingVelocity <= absVelocityX && absVelocityX <= mMaxFlingVelocity
                && absVelocityY < absVelocityX && mSwiping) { // to differentiate with dragging list view
            // dismiss only if flinging in the same direction as dragging
            return (velocityX < 0) == (deltaX < 0);
        }
        return false;
    }

    private void onActionCancel() {
        if (mVelocityTracker == null) return;
        animateToOriginalPosition();
        resetVariables();
    }

    private boolean onActionMove(MotionEvent motionEvent) {
        if (mVelocityTracker == null) return false;
        mVelocityTracker.addMovement(motionEvent);
        float deltaX = motionEvent.getRawX() - mDownX;
        float deltaY = motionEvent.getRawY() - mDownY;
        if (deltaX < 0) return false;
        // deltaY is used to smooth the list view inside
        // deltaX > mSlop to indicate this is swiping and not interacting list view
        if (deltaX > mSlop && Math.abs(deltaY) < deltaX / 2) {
            mSwiping = true;
            mView.getParent().requestDisallowInterceptTouchEvent(true); // so the touch event cant be stop
        }
        if (mSwiping) {
            mTranslationX = deltaX;
            mView.setTranslationX(deltaX);
            return true;
        }
        return false;
    }

    private void animateToOriginalPosition() {
        final int ALPHA_ORIGINAL = 1;
        mView.animate()
                .translationX(ORIGINAL_STAT)
                .alpha(ALPHA_ORIGINAL)
                .setDuration(mAnimationTime)
                .setListener(null);
    }

    private void animateDismiss() {
        int ALPHA_ZERO = 0;
        mView.animate()
                .translationX(mViewWidth)
                .alpha(ALPHA_ZERO)
                .setDuration(mAnimationTime)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCallbacks.onDismiss();
                    }
                });
    }

    private void resetVariables() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
        mTranslationX = ORIGINAL_STAT;
        mDownX = ORIGINAL_STAT;
        mDownY = ORIGINAL_STAT;
        mSwiping = false;
    }

    /**
     * The callback interface used by {@link SwipeDismissTouchListener} to inform its client
     * about a successful dismissal of the view for which it was created.
     */
    public interface DismissCallbacks {
        void onDismiss();
    }
}

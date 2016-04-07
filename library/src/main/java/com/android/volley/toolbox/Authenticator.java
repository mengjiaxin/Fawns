package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;

/**
 * An interface for interacting with auth tokens.
 */
public interface Authenticator {
    /**
     * Synchronously retrieves an auth token.
     *
     * @throws AuthFailureError If authentication did not succeed
     */
    public String getAuthToken() throws AuthFailureError;

    /**
     * Invalidates the provided auth token.
     */
    public void invalidateAuthToken(String authToken);
}

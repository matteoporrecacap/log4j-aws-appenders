// Copyright (c) Keith D Gregory
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.kdgregory.logging.aws.internal.facade;


/**
 *  This exception is thown by {@link SNSFacade} for any situation that
 *  requires intervention by the caller. Each instance has a reason code, and
 *  an indication of whether the condition is retryable. Where relevant, it may
 *  wrap an underlying SDK-specific cause.
 */
public class SNSFacadeException
extends FacadeException
{
    private static final long serialVersionUID = 1L;

    public enum ReasonCode
    {
        /**
         *  An exception that isn't expected to be corrected by the caller (just
         *  throw it on up).
         */
        UNEXPECTED_EXCEPTION,


        /**
         *  An invalid configuration value (these shouldn't happen if you validate
         *  your configuration!). The message will indicate the problem.
         */
        INVALID_CONFIGURATION,


        /**
         *  The requested SNS topic does not exist.
         */
        MISSING_TOPIC,


        /**
         *  The API call was throttled; caller should retry.
         */
        THROTTLING
    }

//----------------------------------------------------------------------------
//  Implementation
//----------------------------------------------------------------------------

    private ReasonCode reasonCode;


    /**
     *  Base constructor.
     */
    public SNSFacadeException(String message, Throwable cause, ReasonCode reasonCode, boolean isRetryable, String functionName, Object... args)
    {
        super(message, cause, isRetryable, functionName, args);
        this.reasonCode = reasonCode;
    }


    /**
     *  Convenience constructor, for conditions where there is no underlying exception,
     *  or where it's irrelevant.
     */
    public SNSFacadeException(String message, ReasonCode reasonCode, boolean isRetryable, String functionName, Object... args)
    {
        this(message, null, reasonCode, isRetryable, functionName, args);
    }


    /**
     *  Convenience constructor for testing. Do not use in normal code!
     */
    public SNSFacadeException(ReasonCode reasonCode, boolean isRetryable, Throwable cause)
    {
        this("use for testing only", cause, reasonCode, isRetryable, null);
    }

//----------------------------------------------------------------------------
//  Accessors
//----------------------------------------------------------------------------

    /**
     *  Returns a code that can be used by the application to dispatch exception handling.
     */
    public ReasonCode getReason()
    {
        return reasonCode;
    }
}
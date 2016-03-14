package com.github.philipmw;

import frege.prelude.PreludeBase;
import org.junit.Test;

import static com.github.philipmw.TrackerV1.payload;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

class Maybe {
    static PreludeBase.TMaybe just(Object o) {
        return PreludeBase.TMaybe.DJust.mk(o);
    }
    static PreludeBase.TMaybe nothing() {
        return PreludeBase.TMaybe.DNothing.mk();
    }
}

public class TrackerTest {
    @Test
    public void allJust() {
        TrackerV1.TSessionParams session = TrackerV1.TSessionParams.mk(
                Maybe.just("data-source"),
                Maybe.just("x"),
                Maybe.just(TrackerV1.TSessionControl.SessionStart),
                Maybe.just("y"));
        TrackerV1.THit hit = TrackerV1.THit.DPageHit.mk(session);
        assertThat(payload(hit),
                   equalTo("ds=data-source&uid=x&geoid=y&sc=start&t=pageview"));
    }

    @Test
    public void someNothing() {
        TrackerV1.TSessionParams session = TrackerV1.TSessionParams.mk(
                Maybe.just("data-source"),
                Maybe.nothing(),
                Maybe.just(TrackerV1.TSessionControl.SessionEnd),
                Maybe.just("y"));
        TrackerV1.THit hit = TrackerV1.THit.DPageHit.mk(session);
        assertThat(payload(hit),
                equalTo("ds=data-source&geoid=y&sc=end&t=pageview"));
    }
}

package com.dervlabs.chirp.domain.events

import java.time.Instant

interface ChirpEvent {
    val eventId: String
    val eventKey: String // information of what specifically happens, what kind of event that is
    val occurredAt: Instant
    val exchange: String // post office that receives all kinds of related events, process these and reroute these to respective queues
}
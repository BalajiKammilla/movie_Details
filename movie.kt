package org.quarkus

import io.quarkus.hibernate.orm.panache.PanacheEntity
import jakarta.persistence.Entity

@Entity
data class movie(
    var movieName: String?= null,
    var director: String?= null,
    var rating: Int?= null,
    var review: String?= null
): PanacheEntity()

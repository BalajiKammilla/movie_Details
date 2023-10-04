package org.quarkus

import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/movie")
class movieResouce(@Inject private val movieRepository: movieRepository) {

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun post(movie: movie):Response{
         movieRepository.persist(movie)
        return Response.ok(movie).build()
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll():List<movie>{
        return movieRepository.listAll()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    fun getById(@PathParam("id") id:Long):movie{
        return movieRepository.findById(id)
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@PathParam("id") id:Long, newmovie: movie):Response{
        //worked properly
    val existMovie=movieRepository.findById(id)

        if (existMovie == null){
            return Response.status(Response.Status.NOT_FOUND).build()
        }

        existMovie.movieName =newmovie.movieName
        existMovie.director = newmovie.director
        existMovie.rating = newmovie.rating
        existMovie.review = newmovie.review

        movieRepository.persist(existMovie)
        return Response.status(Response.Status.OK).entity(existMovie).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun delete(@PathParam("id") id:Long):TransResponse{
        movieRepository.deleteById(id)
        return TransResponse(status = "Movie deleted")
    }

    data class TransResponse(val status: String)
}
package org.acme.rest;

import org.acme.domain.planningSolution.MeetingSchedule;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Path("/meetingSchedule")
public class MeetingScheduleResource {

    @Inject
    SolverManager<MeetingSchedule, UUID> solverManager;

    @POST
    @Path("/solve")
    public MeetingSchedule solve(MeetingSchedule problem) {
        System.out.println("111111111111");

        // TODO: 跑起来后看看xml配置是否生效，运行20秒为生效
        UUID problemId = UUID.randomUUID();
        // Submit the problem to start solving
        SolverJob<MeetingSchedule, UUID> solverJob = solverManager.solve(problemId, problem);
        MeetingSchedule solution;
        try {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }
}

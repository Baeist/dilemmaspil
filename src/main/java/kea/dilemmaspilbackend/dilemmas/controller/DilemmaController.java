package kea.dilemmaspilbackend.dilemmas.controller;

import kea.dilemmaspilbackend.dilemmas.model.DilemmaModel;
import kea.dilemmaspilbackend.dilemmas.service.DilemmaService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
public class DilemmaController {

    // not sure if we should get the dilemma id from url or as another param, chose url here

    private DilemmaService dilemmaService;

    public DilemmaController(DilemmaService dilemmaService){
        this.dilemmaService = dilemmaService;
    }

    @PostMapping("/api/post/create/dilemma")
    public ResponseEntity<String> createDilemma(@RequestBody DilemmaModel dilemmaModel){

        dilemmaService.save(dilemmaModel);

        String msg = "Created new dilemma with the names " + dilemmaModel.getDaName() + "/" + dilemmaModel.getEnName() +
                " and descriptions: " + dilemmaModel.getDaDescription() + " AND " + dilemmaModel.getEnDescription();

        if(dilemmaService.save(dilemmaModel) != null){

            return ResponseEntity.ok(msg);
        }
        else{
            return ResponseEntity.ok("Not created");
        }
    }


    @PostMapping("/api/post/update/{id}/dilemma")
    public ResponseEntity<String> updateDilemma(@RequestBody DilemmaModel dilemmaModel, @PathVariable() Integer id) {

        String msg = "Updated dilemma with the names " + dilemmaModel.getDaName() + "/" + dilemmaModel.getEnName() +
        " and descriptions: " + dilemmaModel.getDaDescription() + " AND " + dilemmaModel.getEnDescription();

        Optional<DilemmaModel> oldDilemma = dilemmaService.findById(id);
        if (oldDilemma.isPresent() && id == dilemmaModel.getId()) {
            dilemmaService.save(dilemmaModel);
            return ResponseEntity.ok(msg);
        }
        else{
            return ResponseEntity.ok("Not updated");
        }
    }

    // doesnt work currently, needs to take care of dependent tables also
    @PostMapping("api/post/delete/{id}/dilemma")
    public ResponseEntity<String> deleteDilemma(@PathVariable() Integer id){

        Optional<DilemmaModel> model = dilemmaService.findById(id);

        if(model.isPresent()) {

            System.out.println(dilemmaService.findById(id));

            dilemmaService.deleteById(id);

            return ResponseEntity.ok("Deleted dilemma");
        }
        else{
            return ResponseEntity.ok("Did not find requested dilemma");
        }
    }

    @GetMapping("/api/get/findall/dilemma")
    public ResponseEntity<Set<DilemmaModel>> findAllDilemmas(){

        Set<DilemmaModel> models =  dilemmaService.findAll();

        System.out.println("Found all dilemmas");

        return ResponseEntity.ok(models);
    }

    @GetMapping("/api/get/find/{id}/dilemma")
    public ResponseEntity<Optional<DilemmaModel>> getDilemmaById(@PathVariable() Integer id){

        Optional<DilemmaModel> model = dilemmaService.findById(id);

        if(model.isPresent()){

            System.out.printf("Dilemma found: ");

            return ResponseEntity.ok(model);
        }
        else{
            System.out.printf("Dilemma not found");

            return ResponseEntity.ok(null);
        }
    }


}

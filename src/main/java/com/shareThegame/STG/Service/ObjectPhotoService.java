package com.shareThegame.STG.Service;

import com.shareThegame.STG.Model.ObjectPhotos;
import com.shareThegame.STG.Repository.ObjectPhotosRepository;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public
class ObjectPhotoService {
    @Autowired
    ObjectPhotosRepository objectPhotosRepository;

    @Transactional
    public
    void saveImageFile ( MultipartFile file , Long sportobjectid ) {
//
//        try {
//
//
//            byte[] byteObjects = new byte[file.getBytes().length];
//
//            int i = 0;
//
//            for (byte b : file.getBytes()){
//                byteObjects[i++] = b;
//            }
//            ObjectPhotos objectPhotos=new ObjectPhotos ();
//
//            objectPhotos.setSportobjectid ( sportobjectid );
//            objectPhotos.setPhoto ( byteObjects );
//
//
//            objectPhotosRepository.save(objectPhotos);
//        } catch (IOException e) {
//
//
//            e.printStackTrace();
//        }
//    }
    }
}

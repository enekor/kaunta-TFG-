package com.kunta.kaunta_api.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kunta.kaunta_api.exception.StorageException;
import com.kunta.kaunta_api.exception.StorageFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService{

    // Directorio raiz de nuestro almacén de ficheros

    private final Path rootLocation;

    @Value("${rutaImagenes}")
    private String rutaImagenes;


    public FileSystemStorageService(@Value("") String path) {
        this.rootLocation = Paths.get(path);
    }

    /**
     * Método que almacena un fichero en el almacenamiento secundario
     * desde un objeto de tipo MultipartFile
     *
     * Modificamos el original del ejemplo de Spring para cambiar el nombre
     * del fichero a almacenar. Como lo asociamos al Empleado que se ha
     * dado de alta, usaremos el ID de empleado como nombre de fichero.
     *
     */
    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            Files.createDirectories(Path.of(rutaImagenes));
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Path.of(rutaImagenes+File.separator+filename),
                        StandardCopyOption.REPLACE_EXISTING);

                String ret = filename.replace("http://localhost:7777","");
                return ret;
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

    }

    /**
     * Método que devuelve la ruta de todos los ficheros que hay
     * en el almacenamiento secundario del proyecto.
     */
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    /**
     * Método que es capaz de cargar un fichero a partir de su nombre
     * Devuelve un objeto de tipo Path
     */
    @Override
    public Path load(String filename) {
        return Path.of(rutaImagenes+File.separator+filename);
    }


    /**
     * Método que es capaz de cargar un fichero a partir de su nombre
     * Devuelve un objeto de tipo Resource
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }


    /**
     * Método que elimina todos los ficheros del almacenamiento
     * secundario del proyecto.
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    /**
     * Método que inicializa el almacenamiento secundario del proyecto
     */
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    /**
     * Método que es capaz de borrar un archivo por su nombre
     */
    @Override
    public void delete(String filename) {
        String justFilename = StringUtils.getFilename(filename);
        try {
            Path file = load(justFilename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Error al eliminar un fichero", e);
        }

    }

}

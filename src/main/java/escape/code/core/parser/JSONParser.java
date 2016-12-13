package escape.code.core.parser;


public interface JSONParser {

    /**
     * Imports data from a JSON file into an array of models
     *
     * EXAMPLE:
     * StudentDto[] = jsonParser.read(StudentDto[].class, "res/students.json");
     *
     * @param classes - class of the model array to be filled
     * @param filePath - path to the JSON file
     * @param <E> - model
     * @return - model array filled with data from JSON file
     */
    <E> E[] read(Class<E[]> classes, String filePath);

    /**
     * Exports data from a model to a JSON file
     *
     * @param entity - model to be exported
     * @param filePath - path to the JSON file
     * @param <E> - model
     */
    <E> void write(E entity, String filePath);
}

import java.io.Serializable;


public class Pet implements Serializable {
    private String aniD;
    private String name;
    private String breed;
    private int age;
    private String gender; 
    private boolean adopted;
    private static final long serialVersionUID = 1L;  // Add this line


    public Pet(String aniD, String name, String breed, int age, String gender) {
        this.aniD = aniD;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.adopted = false; // Default value for adopted
    }

    public String getName(){
        return name;
    }

    public String getBreed(){
        return breed;
    }

    public String getSpecies(){ //This method returns the species of the pet based on the first letter of the aniD
                                // aniD format: "D_12345" for Dog, "C_12345" for Cat, etc.

        switch (aniD.split("_")[0]) {
            case "D":
                return "Dog";
            case "C":
                return "Cat";
            case "B":
                return "Bird";
            case "R":
                return "Reptile";
            case "F":
                return "Fish";
            default:
                return "Unknown";
        }
        

    }

    public String getId(){
        return aniD;
    }

    public int getAge(){
        return age;
    }

    public String getGender() {
        return gender;
    }

  
    public boolean getAdoptionStat(){
        return adopted;
    }

    public void setAdoptionStat(boolean adopted) {
        this.adopted = adopted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(String aniD) {
        this.aniD = aniD;
    }

    public String toString() {
        return "Pet ID: " + aniD + ", Name: " + name + ", Breed: " + breed + ", Age: " + age + ", Adopted: " + adopted;
    }
    public boolean isAvalibleForAdoption() {
        return !adopted;
    }
}
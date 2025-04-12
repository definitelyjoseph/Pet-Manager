public class Pet implements Animal {
    private String aniD;
    private String name;
    private String breed;
    private int age;
    private boolean adopted;

    public Pet(String aniD, String name, String breed, int age) {
        this.aniD = aniD;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.adopted = false; // Default value for adopted
    }

    public String getName(){
        return name;
    }
 public void setName(String name){
        this.name = name;
    }
    public String getBreed(){
        return breed;
    }
    public void setBreed(String breed){
        this.breed = breed;
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
    public void setId(String aniD){
        this.aniD = aniD;
    }
    
   

    public int getAge(){
        return age;
    }
 public void setAge(int age){
        this.age = age;
    }
  
    public boolean getAdoptionStat(){
        return adopted;
    }

    public void setAdoptionStat(boolean adopted) {
        this.adopted = adopted;
    }

    public String toString() {
        return "Pet ID: " + aniD + ", Name: " + name + ", Breed: " + breed + ", Age: " + age + ", Adopted: " + adopted;
 
    }

}

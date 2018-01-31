package vo;

public class Question {
  private int q_id;
  private int r_id;
  private int t_id;
  private String tag;
  private String q_title;
  private String q_description;
  private String q_author;
  private int q_time_limit;
  private int q_space_limit;
  private String q_input;
  private String q_output;

  public int getQ_id() {
    return q_id;
  }

  public void setQ_id(int q_id) {
    this.q_id = q_id;
  }

  public int getR_id() {
    return r_id;
  }

  public void setR_id(int r_id) {
    this.r_id = r_id;
  }

  public int getT_id() {
    return t_id;
  }

  public void setT_id(int t_id) {
    this.t_id = t_id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getQ_title() {
    return q_title;
  }

  public void setQ_title(String q_title) {
    this.q_title = q_title;
  }

  public String getQ_description() {
    return q_description;
  }

  public void setQ_description(String q_description) {
    this.q_description = q_description;
  }

  public String getQ_author() {
    return q_author;
  }

  public void setQ_author(String q_author) {
    this.q_author = q_author;
  }

  public int getQ_time_limit() {
    return q_time_limit;
  }

  public void setQ_time_limit(int q_time_limit) {
    this.q_time_limit = q_time_limit;
  }

  public int getQ_space_limit() {
    return q_space_limit;
  }

  public void setQ_space_limit(int q_space_limit) {
    this.q_space_limit = q_space_limit;
  }

  public String getQ_input() {
    return q_input;
  }

  public void setQ_input(String q_input) {
    this.q_input = q_input;
  }

  public String getQ_output() {
    return q_output;
  }

  public void setQ_output(String q_output) {
    this.q_output = q_output;
  }
}
